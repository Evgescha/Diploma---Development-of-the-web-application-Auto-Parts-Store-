package com.hescha.autochapterstore.controller;

import com.hescha.autochapterstore.model.Order;
import com.hescha.autochapterstore.model.OrderItem;
import com.hescha.autochapterstore.model.OrderStatus;
import com.hescha.autochapterstore.model.Product;
import com.hescha.autochapterstore.model.User;
import com.hescha.autochapterstore.service.OrderItemService;
import com.hescha.autochapterstore.service.OrderService;
import com.hescha.autochapterstore.service.ProductService;
import com.hescha.autochapterstore.service.SecurityService;
import com.hescha.autochapterstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping(OrderController.CURRENT_ADDRESS)
public class OrderController {
    public static final String API_PATH = "order";
    public static final String CURRENT_ADDRESS = "/" + API_PATH;
    public static final String MESSAGE = "message";
    public static final String THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE = API_PATH;
    public static final String THYMELEAF_TEMPLATE_ONE_ITEM_PAGE = THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE + "-one";
    public static final String THYMELEAF_TEMPLATE_EDIT_PAGE = THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE + "-edit";
    public static final String REDIRECT_TO_ALL_ITEMS = "redirect:" + CURRENT_ADDRESS;

    private final OrderService orderService;

    private final UserService userService;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final SecurityService securityService;

    @GetMapping
    public String readAll(Model model) {
        User loggedIn = securityService.getLoggedIn();
        model.addAttribute("list", orderService.readAll());
        model.addAttribute("user", loggedIn);
        return THYMELEAF_TEMPLATE_ALL_ITEMS_PAGE;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entity", orderService.read(id));
        return THYMELEAF_TEMPLATE_ONE_ITEM_PAGE;
    }

    @GetMapping("/{id}/changeStatus")
    public String changeStatus(@PathVariable("id") Long id, OrderStatus status) {
        Order read = orderService.read(id);
        read.setStatus(status);
        orderService.update(read);
        return REDIRECT_TO_ALL_ITEMS;
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String editPage(Model model, @PathVariable(name = "id", required = false) Long id) {
        if (id == null) {
            model.addAttribute("entity", new Order());
        } else {
            model.addAttribute("entity", orderService.read(id));
        }

        model.addAttribute("user_list", userService.readAll());
        model.addAttribute("orderItem_list", orderItemService.readAll());

        return THYMELEAF_TEMPLATE_EDIT_PAGE;
    }

    @PostMapping
    public String save(@ModelAttribute Order entity, RedirectAttributes ra) {
        if (entity.getId() == null) {
            try {
                Order createdEntity = orderService.create(entity);
                ra.addFlashAttribute(MESSAGE, "Creating is successful");
                return REDIRECT_TO_ALL_ITEMS + "/" + createdEntity.getId();
            } catch (Exception e) {
                ra.addFlashAttribute(MESSAGE, "Creating failed");
                e.printStackTrace();
            }
            return REDIRECT_TO_ALL_ITEMS;
        } else {
            try {
                orderService.update(entity.getId(), entity);
                ra.addFlashAttribute(MESSAGE, "Editing is successful");
            } catch (Exception e) {
                e.printStackTrace();
                ra.addFlashAttribute(MESSAGE, "Editing failed");
            }
            return REDIRECT_TO_ALL_ITEMS + "/" + entity.getId();
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            orderService.delete(id);
            ra.addFlashAttribute(MESSAGE, "Removing is successful");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute(MESSAGE, "Removing failed");
        }
        return REDIRECT_TO_ALL_ITEMS;
    }

    @GetMapping("/{id}/deleteOrderItem/{itemId}")
    public String deleteOrderItem(@PathVariable Long id, @PathVariable Long itemId, RedirectAttributes ra) {
        try {
            Order order = orderService.read(id);
            Optional<OrderItem> first = order.getOrderitems()
                    .stream()
                    .filter(orderItem -> Objects.equals(orderItem.getId(), itemId))
                    .findFirst();
            if (first.isPresent()) {
                OrderItem orderItem = first.get();
                if (orderItem.getCount() <= 1) {
                    order.getOrderitems().remove(orderItem);
                    orderService.update(order);
                    orderItem.setProduct(null);
                    orderItemService.update(orderItem);
                    orderItemService.delete(orderItem);
                } else {
                    orderItem.setCount(orderItem.getCount() - 1);
                    orderItemService.update(orderItem);
                }
            }
            ra.addFlashAttribute(MESSAGE, "Removing is successful");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute(MESSAGE, "Removing failed");
        }
        return REDIRECT_TO_ALL_ITEMS;
    }


    @GetMapping("/addProduct/{productId}")
    public String addProductToOrder(@PathVariable Long productId,
                                    @RequestParam(defaultValue = "1", required = false) Integer count,
                                    RedirectAttributes ra) {
        User loggedInUser = securityService.getLoggedIn();
        Product product = productService.read(productId);
        Order order = getOrCreateOrder(loggedInUser);
        createOrUpdateOrderitem(productId, product, order, count);
        ra.addFlashAttribute("message", "Product added to cart");
        return "redirect:/order";
    }

    private Order getOrCreateOrder(User loggedInUser) {
        Order order;
        Optional<Order> optionalOrder = loggedInUser.getOrders().stream()
                .filter(order1 -> order1.getStatus() == OrderStatus.CREATED)
                .findFirst();
        if (optionalOrder.isEmpty()) {
            order = new Order();
            order.setStatus(OrderStatus.CREATED);
            order = orderService.create(order);
            loggedInUser.getOrders().add(order);
            userService.update(loggedInUser);
        } else {
            order = optionalOrder.get();
        }
        return order;
    }

    private void createOrUpdateOrderitem(Long productId, Product product, Order order, Integer count) {
        Optional<OrderItem> existingOrderItem = order.getOrderitems().stream()
                .filter(orderItem -> orderItem.getProduct().getId() == productId)
                .findFirst();

        if (existingOrderItem.isEmpty()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setCount(count);
            orderItem = orderItemService.create(orderItem);
            order.getOrderitems().add(orderItem);
            orderService.update(order);
        } else {
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setCount(orderItem.getCount() + count);
            orderItemService.update(orderItem);
        }
    }
}

package com.hescha.autochapterstore.service;

import com.hescha.autochapterstore.model.Order;
import com.hescha.autochapterstore.model.OrderItem;
import com.hescha.autochapterstore.model.User;
import com.hescha.autochapterstore.repository.OrderItemRepository;
import com.hescha.autochapterstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService extends CrudService<Order> {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    public OrderService(OrderRepository repository, OrderItemRepository orderItemRepository,
                        UserService userService) {
        super(repository);
        this.repository = repository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    public Order update(Long id, Order entity) {
        Order read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity Order not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(Order entity, Order read) {
        read.setOrderitems(entity.getOrderitems());
        read.setStatus(entity.getStatus());
    }

    public void delete(Long orderId) {
        Order order = read(orderId);
        User owner = userService.findByOrdersContains(order);
        List<OrderItem> orderItems = order.getOrderitems();
        // Удаление связи между Order и OrderItem
        for (OrderItem orderItem : orderItems) {
            orderItem.setProduct(null);
            orderItemRepository.save(orderItem);
        }
        order.setOrderitems(new ArrayList<>());
        update(order);

        // Удаление OrderItem
        orderItemRepository.deleteAll(orderItems);

        Order order2 = owner.getOrders().stream()
                .filter(order1 -> order1.getId() == orderId)
                .findFirst()
                .get();
        if (order2 != null) {
            owner.getOrders().remove(order);
            userService.update(owner);
        }

        // Удаление самого заказа
        repository.deleteById(orderId);
    }
}

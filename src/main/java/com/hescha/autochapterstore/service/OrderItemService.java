package com.hescha.autochapterstore.service;

import com.hescha.autochapterstore.model.OrderItem;
import com.hescha.autochapterstore.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService extends CrudService<OrderItem> {

    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public OrderItem update(Long id, OrderItem entity) {
        OrderItem read = read(id);
        if (read == null) {
            throw new RuntimeException("Entity OrderItem not found");
        }
        updateFields(entity, read);
        return update(read);

    }

    private void updateFields(OrderItem entity, OrderItem read) {
        read.setProduct(entity.getProduct());
        read.setCount(entity.getCount());
    }
}


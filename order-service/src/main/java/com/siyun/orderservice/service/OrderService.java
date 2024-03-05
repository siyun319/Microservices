package com.siyun.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siyun.orderservice.dto.OrderLineItemsDto;
import com.siyun.orderservice.dto.OrderRequest;
import com.siyun.orderservice.model.Order;
import com.siyun.orderservice.model.OrderLineItems;
import com.siyun.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;


// Transactions ensure data integrity by
//  guaranteeing that a series of operations either all succeed or all fail together.
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    
    private final OrderRepository orderRepository;
    public void palceOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(
            e -> mapToDto(e)).toList();
        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto e) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(e.getPrice());
        orderLineItems.setQuantity(e.getQuantity());
        orderLineItems.setSkuCode(e.getSkuCode());
        return orderLineItems;
    }
    
}

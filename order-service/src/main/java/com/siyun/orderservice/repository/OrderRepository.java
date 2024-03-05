package com.siyun.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siyun.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}

package com.book_store.repository;

import com.book_store.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    public List<OrderDetail> findAllByOrderId(int orderId);
}

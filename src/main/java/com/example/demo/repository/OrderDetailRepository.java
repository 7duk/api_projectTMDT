package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    String INSERT_ORDER_DETAIL = "INSERT INTO `order_detail`(ORDER_ID, ITEM_DETAIL_ID, AMOUNT, NOTE)" +
            "VALUES (:ORDER_ID, :ITEM_DETAIL_ID,:AMOUNT, :NOTE)";
    @Modifying
    @Query(value = INSERT_ORDER_DETAIL, nativeQuery = true)
    void saveOrderDetail(@Param("ORDER_ID")Integer orderId,@Param("ITEM_DETAIL_ID")Integer itemDetailId,
                   @Param("AMOUNT")Integer amount,@Param("NOTE")String note);
    @Query(value = "SELECT * FROM `order_detail`", nativeQuery = true)
    List<OrderDetail> getAllOrderDetail();


    @Query(value = "SELECT * FROM `order_detail` WHERE `order_detail`.ORDER_ID = :orderId", nativeQuery = true)
    List<OrderDetail> getAllOrderDetailByOrderId(@Param("orderId") Integer orderId);
}

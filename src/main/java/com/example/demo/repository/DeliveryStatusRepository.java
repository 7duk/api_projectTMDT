package com.example.demo.repository;

import com.example.demo.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus,Integer> {

    @Query(value = "SELECT * FROM `delivery_status` WHERE `delivery_status`.ID = :DeStatusId", nativeQuery = true)
    DeliveryStatus getDelivetyStausById(@Param("DeStatusId") Integer DeStatusId);

//    @Query(value = "SELECT *\n" +
//                    "FROM order_delivery_status_detail\n" +
//                    "JOIN `order` ON order_delivery_status_detail.ORDER_ID = `order`.ID\n" +
//                    "WHERE `order`.CUSTOMER_ID = :CustomerId AND order_delivery_status_detail.DELIVERY_STATUS_ID =:DeStatusId;",
//                    nativeQuery = true)
//    DeliveryStatus getOrderDeliveryByIdDelivery(@Param("DeStatusId") Integer CustomerId, @Param("DeStatusId") Integer DeliveryId);
}

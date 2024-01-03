package com.example.demo.repository;

import com.example.demo.entity.OrderDeliveryStatusDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderDeliveryStatusDetailRepository extends JpaRepository<OrderDeliveryStatusDetail,Integer> {
    String INSERT_ODSD = "INSERT INTO `order_delivery_status_detail`(DELIVERY_STATUS_ID," +
            "ORDER_ID,LAST_UPDATE_AT,`DESC`) VALUES(:dsId,:oId,:lua,:desc)";
    @Modifying
    @Query(value = INSERT_ODSD,nativeQuery = true)
    void saveODSD(@Param("dsId") Integer dsId, @Param("oId") Integer oId, @Param("lua")LocalDateTime lua
            , @Param("desc") String desc);
    @Modifying
    @Query(value = "UPDATE order_delivery_status_detail SET DELIVERY_STATUS_ID = :DS,LAST_UPDATE_AT=:LUA,`DESC`=:DESC " +
            "WHERE ORDER_ID= :ORDER_ID",nativeQuery = true)
    Integer updateDeliveryStatusByOrderId(@Param("DS") Integer ds,@Param("LUA") LocalDateTime lua
            ,@Param("DESC") String desc,@Param("ORDER_ID") Integer orderID);
}

package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    String INSERT_ORDER = "INSERT INTO `order`(DELIVERY_ADDRESS, TOTAL_FEE, DELIVERY_DATE, CREATE_AT," +
            " CUSTOMER_ID, PAYMENT_METHOD_ID, MAGIAODICH,STATUS_PAYMENT,PHONE,NAME)" +
            "VALUES (:#{#order.getDeliveryAddress()}, :#{#order.getTotalFee()},:#{#order.getDeliveryDate()}, " +
            ":#{#order.getCreateAt()}, " +
            ":#{#order.getCustomerId()},  :#{#order.getPayMethodId()}, :#{#order.getMaGiaoDich()}," +
            ":#{#order.getStatusPayment()},:#{#order.getPhone()},:#{#order.getName()})";

    @Modifying
    @Query(value = INSERT_ORDER, nativeQuery = true)
    Integer saveOrder(@Param("order") Order order);

    @Query(value = "SELECT * FROM `order` ORDER BY CASE WHEN :SORT = 'DESC' THEN ID END DESC, CASE WHEN :SORT = 'ASC' THEN ID END ASC LIMIT :LIMIT OFFSET :OFFSET", nativeQuery = true)
    List<Order> getAllOrder(@Param("LIMIT")Integer limit,@Param("OFFSET") Integer offSet,@Param("SORT")String sort);
    @Query(value = "SELECT COUNT(*) FROM `order`", nativeQuery = true)
    int getTotalOrderCount();

    @Query(value = "SELECT COUNT(*), COALESCE(SUM(TOTAL_FEE), 0) FROM `order`", nativeQuery = true)
    List<Object[]> getCountAndTotalOrderFee();

    @Query(value = "SELECT * FROM `order` ORDER BY `order`.ID DESC LIMIT 1", nativeQuery = true)
    Order getOrderLastIndexOf();

    @Query(value = "SELECT * FROM `order` WHERE `order`.ID = :orderId", nativeQuery = true)
    Order getOrderById(@Param("orderId") Integer orderId);

    @Query(value = "SELECT * FROM `order` WHERE `order`.CUSTOMER_ID = :customerId ORDER BY CASE WHEN :SORT = 'DESC' THEN ID END DESC, CASE WHEN :SORT = 'ASC' THEN ID END ASC LIMIT :LIMIT OFFSET :OFFSET", nativeQuery = true)
    List<Order> getOrderByCustomerId(@Param("customerId") Integer customerId,@Param("LIMIT")Integer limit,@Param("OFFSET") Integer offSet,@Param("SORT")String sort);

    @Query(value = "SELECT COUNT(*), COALESCE(SUM(TOTAL_FEE), 0) FROM `order` WHERE `order`.CUSTOMER_ID = :customerId", nativeQuery = true)
    List<Object[]> getTotalOrderCountByCustomer(@Param("customerId") Integer customerId);

    @Query(value = "SELECT * FROM `order` WHERE `order`.CREATE_AT BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Order> getOrderByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Modifying
    @Query(value = "UPDATE order SET STATUS_PAYMENT = 1 WHERE ID= :ID",nativeQuery = true)
    Integer updateStatusPayment(@Param("ID") Integer id);
}

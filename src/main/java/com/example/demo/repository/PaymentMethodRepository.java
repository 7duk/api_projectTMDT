package com.example.demo.repository;

import com.example.demo.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {
    List<PaymentMethod> getPaymentMethodsByIsDelete(Integer deleteValue);
    @Modifying
    @Query(value = "INSERT INTO payment_method(DESC, IS_DELETE) VALUES(:desc,:check) ",nativeQuery = true)
    int savePaymentMethod(@Param("desc")String DESC, @Param("check") Boolean check);
    @Query(value = "SELECT ID FROM payment_method WHERE payment_method.DESC = :desc  AND  payment_method.IS_DELETE =:check",nativeQuery = true)
    Integer getIdByDescription(@Param("desc") Integer DESC, @Param("check") Boolean check);
    @Query(value = "SELECT DESC FROM payment_method WHERE payment_method.ID=:id",nativeQuery = true)
    Integer getMethodBYId(@Param("id") Integer ID);

}

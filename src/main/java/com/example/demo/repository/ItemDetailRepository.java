package com.example.demo.repository;

import com.example.demo.entity.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetail,Integer> {
    @Modifying
    @Query(value = "UPDATE `item_detail` SET AMOUNT = :AMOUNT WHERE ID = :ID",nativeQuery = true)
     void updateAmountById(@Param("ID") Integer id,@Param("AMOUNT") Integer amount);
    @Query(value = "SELECT * FROM `item_detail` WHERE ID = :ID",nativeQuery = true)
    ItemDetail getItemDetailById(@Param("ID") Integer id);


}

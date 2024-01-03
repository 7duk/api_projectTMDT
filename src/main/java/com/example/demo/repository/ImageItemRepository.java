package com.example.demo.repository;

import com.example.demo.entity.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageItemRepository extends JpaRepository<ImageItem,Integer> {
    @Modifying
    @Query(value = "DELETE FROM image_item WHERE ID =:ID",nativeQuery = true)
    Integer removeImageItemById(@Param("ID") Integer id);
    @Modifying
    @Query(value = "DELETE FROM image_item WHERE ITEM_ID =:ITEM_ID",nativeQuery = true)
    Integer removeImageItemByItemId(@Param("ITEM_ID") Integer id);
}

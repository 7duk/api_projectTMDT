package com.example.demo.repository;

import com.example.demo.entity.ItemGroup;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup,Integer> {
    List<ItemGroup> getItemGroupsByIsDelete(Integer deleteValue);
    @Modifying
    @Query(value = "UPDATE item_group SET IS_DELETE = 1 WHERE ID=:id",nativeQuery = true)
    int removeIgById(@Param("id") Integer igId);
}

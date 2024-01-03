package com.example.demo.repository;

import com.example.demo.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Modifying
    @Query(value = "INSERT INTO color(DESCRIPTION) VALUES(:code)",nativeQuery = true)
    int saveColor(@Param("code") String color);
    @Query(value = "SELECT ID FROM color WHERE color.DESCRIPTION = :code",nativeQuery = true)
    Integer getIdByDescription(@Param("code") String code);

}

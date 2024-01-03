package com.example.demo.repository;

import com.example.demo.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query(value = "SELECT * FROM message WHERE CHAT_ID = :CHAT_ID ORDER BY CASE WHEN :SORT = 'DESC' THEN ID END DESC, CASE WHEN :SORT = 'ASC' THEN ID END ASC LIMIT :LIMIT OFFSET :OFFSET", nativeQuery = true)
    List<Message> findAllByChatId(
            @Param("CHAT_ID") Integer cId,
            @Param("LIMIT") Integer limit,
            @Param("OFFSET") Integer off,
            @Param("SORT") String sort
    );

}

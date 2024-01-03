package com.example.demo.repository;

import com.example.demo.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    @Modifying
    @Query(value = "INSERT INTO chat (PARTICIPANTL_1,PARTICIPANTL_2) VALUES(:param1,:param2) ",nativeQuery = true)
    int saveChat(@Param("param1")Integer PARTICIPANTL_1, @Param("param2") Integer PARTICIPANTL_2);
    @Query(value = "SELECT id FROM chat WHERE (chat.PARTICIPANTL_2 = :param2  AND  chat.PARTICIPANTL_1 =:param1) " +
            "OR (chat.PARTICIPANTL_2 = :param1  AND  chat.PARTICIPANTL_1 =:param2)",nativeQuery = true)
    Integer findChatIdByParticipant(@Param("param1") Integer PARTICIPANTL_1,@Param("param2") Integer PARTICIPANTL_2);

}

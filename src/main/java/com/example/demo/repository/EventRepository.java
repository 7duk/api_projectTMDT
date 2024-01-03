package com.example.demo.repository;

import com.example.demo.entity.Event;
import com.example.demo.entity.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    @Modifying
    @Query(value = "INSERT INTO event(TITLE,IMAGE,DESCRIPTION,START_DATE,END_DATE) VALUES(:TITLE,:IMAGE,:DESCRIPTION,:START_DATE,:END_DATE)", nativeQuery = true)
    Integer saveEvent(@Param("TITLE") String title, @Param("IMAGE") String image, @Param("DESCRIPTION") String desc, @Param("START_DATE") LocalDateTime startDate, @Param("END_DATE") LocalDateTime endDate);
    @Query(value = "SELECT * FROM event WHERE TITLE = :TITLE AND DESCRIPTION= :DESCRIPTION",nativeQuery = true)
    Optional<Event> getEventByTilleAndDescription(@Param("TITLE") String title,@Param("DESCRIPTION") String desc);
}

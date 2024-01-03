package com.example.demo.repository;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Gifcode;
import com.example.demo.entity.ImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GifcodeRepository extends JpaRepository<Gifcode,Integer> {
    @Modifying
    @Query(value = "INSERT INTO gifcode(CODE,REMAINING_USAGE,EXPIRATION_DATE,EVENT_ID,DISCOUNT_CODE) VALUES(:CODE,:REMAINING_USAGE,:EXPIRATION_DATE,:EVENT_ID,:DISCOUNT_CODE)", nativeQuery = true)
    Integer saveGifcode(@Param("CODE") String code, @Param("REMAINING_USAGE") Integer remainingUsage, @Param("EXPIRATION_DATE") LocalDateTime expDate, @Param("EVENT_ID") Integer eventId, @Param("DISCOUNT_CODE")Double discountCode);
    @Modifying
    @Query(value = "DELETE FROM gifcode WHERE EVENT_ID=:eventID",nativeQuery = true)
    Integer deleteGifcodesByEventId(@Param("eventID") Integer id);
    @Query(value = "SELECT DISCOUNT_CODE FROM gifcode WHERE CODE=:code",nativeQuery = true)
    Double getDiscountByCode(@Param("code") String code);
    @Query(value = "SELECT REMAINING_USAGE FROM gifcode WHERE CODE=:code",nativeQuery = true)
    Integer getRemainingUsageByCode(@Param("code") String code);
    @Modifying
    @Query(value = "UPDATE  gifcode SET REMAINING_USAGE =:RU WHERE CODE=:code",nativeQuery = true)
    Integer updateRemainingUsageByCode(@Param("RU") Integer remainingUsage,@Param("code")String code);

}

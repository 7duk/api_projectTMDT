package com.example.demo.repository;

import com.example.demo.entity.Gifcode;
import com.example.demo.entity.ItemDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@Transactional
public class GifcodeDao {
    @Autowired
    private JdbcTemplate template;
    String INSERT_GIFCODE = "INSERT INTO gifcode(CODE,REMAINING_USAGE,EXPIRATION_DATE,EVENT_ID,DISCOUNT_CODE) VALUES(?,?,?,?,?)";
    @Modifying
    public Integer saveGifcodesWithEventTogether(List<Gifcode> gifCodes) {
        try{
            template.batchUpdate(INSERT_GIFCODE,gifCodes,100,(PreparedStatement ps, Gifcode i)->{
                ps.setString(1,i.getCode());
                ps.setInt(2,i.getRemainingUsage());
                ps.setString(3,i.getExpirationDate().toString());
                ps.setInt(4,i.getEventId());
                ps.setDouble(5,i.getDiscountCode());
            });
        }
        catch (Exception e){
            return 0;
        }
        return  1;

    }
}

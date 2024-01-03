package com.example.demo.repository;

import com.example.demo.entity.Gifcode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate template;
    String SELECT_TOP_EMAIL_ORDER = "SELECT DISTINCT u.email, COUNT(*) AS order_count\n" +
            "FROM shopiec.user u\n" +
            "JOIN `order` o ON u.ID = o.CUSTOMER_ID\n" +
            "WHERE u.STATUS_ACCOUNT =1 AND u.ROLE_ID =1 AND u.IS_DELETE =0\n" +
            "GROUP BY u.ID ORDER BY order_count DESC LIMIT ?";
    public List<ResultObject> getEmailsTopRatingOrder(Integer limit) {
        try{
            return template.query(SELECT_TOP_EMAIL_ORDER, new Object[]{limit}, BeanPropertyRowMapper.newInstance(ResultObject.class));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Getter
    @Setter
    public static class ResultObject {
        private String email;
        private int orderCount;

    }
}

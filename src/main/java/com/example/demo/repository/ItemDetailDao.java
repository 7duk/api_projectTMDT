package com.example.demo.repository;

import com.example.demo.entity.ItemDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ItemDetailDao {
    @Autowired
    private JdbcTemplate template;
    String INSERT_ITEM_DETAIL = "INSERT INTO item_detail(ITEM_ID,COLOR_ID,AMOUNT) VALUES(?,?,?)";
    @Modifying
    public void saveItemDetails(List<ItemDetail> itemDetails) {
        template.batchUpdate(INSERT_ITEM_DETAIL,itemDetails,100,(PreparedStatement ps, ItemDetail i)->{
            ps.setInt(1,i.getItemId());
            ps.setInt(2,i.getColorId());
            ps.setInt(3,i.getAmount());
        });
    }
}


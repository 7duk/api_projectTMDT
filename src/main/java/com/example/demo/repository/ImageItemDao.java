package com.example.demo.repository;

import com.example.demo.dto.imageitem.ImageSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ImageItemDao {
    @Autowired
    private JdbcTemplate template;
    String INSERT_IMAGE = "INSERT INTO image_item(IMAGE,ITEM_ID) VALUES(?,?)";
    public void saveImages(List<ImageSaveDto> images) {
        template.batchUpdate(INSERT_IMAGE, images, 100, (PreparedStatement ps, ImageSaveDto it) -> {
            ps.setString(1, it.getImage());
            ps.setInt(2, it.getItemId());
        });
    }
}

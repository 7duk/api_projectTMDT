package com.example.demo.service;

import com.example.demo.dto.imageitem.ImageSaveDto;
import com.example.demo.dto.response.Response;
import com.example.demo.repository.ImageItemDao;
import com.example.demo.repository.ImageItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageItemService{
    private final ImageItemDao dao;
    private final ImageItemRepository repository;
    public ResponseEntity<?> saveImageItems(List<ImageSaveDto> imageSaveDtos){
        try{
            dao.saveImages(imageSaveDtos);
        }
        catch (Exception e){
            return  new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> removeImageItemById(Integer id) {
        if(repository.removeImageItemById(id)==1){
            return new ResponseEntity<>( Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>( Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);

    }
    public ResponseEntity<?> removeImageItemByItemId(Integer itemId) {
        if(repository.removeImageItemByItemId(itemId)==1){
            return  new ResponseEntity<>( Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>( Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
    }
}


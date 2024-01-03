package com.example.demo.service;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.color.ColorIdDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Color;
import com.example.demo.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorService {
    private final ColorRepository repository;
    private final ModelMapper mapper;
    public ResponseEntity<?> saveColor(ColorDto colorDto){
        try{
            Color color = mapper.map(colorDto, Color.class);
            if(repository.saveColor(color.getCode())==1){
                return new ResponseEntity<>(Response.builder().message("SUCCCESS").build(), HttpStatus.CREATED);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  null;
    }

    public ResponseEntity<?> getColorIdByCode(String code) {
        Integer colorId = repository.getIdByDescription(code);
        return new ResponseEntity<>(ColorIdDto.builder().ColorId(colorId).build(),HttpStatus.OK);
    }

    public ResponseEntity<?> getAllColor() {
        List<ColorDto> colors = repository.findAll().stream().map(e-> mapper.map(e,ColorDto.class)).toList();
        if(!colors.isEmpty()){
            return new ResponseEntity<>(colors, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

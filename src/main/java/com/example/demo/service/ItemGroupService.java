package com.example.demo.service;

import com.example.demo.dto.imageitem.ImageItemDto;
import com.example.demo.dto.item.ItemDto;
import com.example.demo.dto.itemgroup.ItemGroupDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.ItemGroup;
import com.example.demo.repository.ItemGroupRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemGroupService {
    private final ItemGroupRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<?> saveIGroup(ItemGroupDto itemGroupDto){
        ItemGroup itemGroup = mapper.map(itemGroupDto, ItemGroup.class);
        itemGroup.setIsDelete(0);
        try {
            repository.save(itemGroup);
            return new ResponseEntity<>(Response.builder().message("INSERT ITEMGROUP SUCCESS").build(), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<ItemGroupDto>> getIGroupsIfNotDeleted(Integer deleteValue){
        List<ItemGroup> igroups = repository.getItemGroupsByIsDelete(deleteValue);
        List<ItemGroupDto> igroupDtos = igroups.stream().map(e->{
            return mapper.map(e, ItemGroupDto.class);
        }).toList();
        if(!igroupDtos.isEmpty()){
            return new ResponseEntity<>(igroupDtos,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    public ResponseEntity<?> removeIgById(Integer igId){
        if(repository.removeIgById(igId)==1){
            return new ResponseEntity<>( Response.builder().message("SUCCESS").build(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>( Response.builder().message("ID IS NOT EXIST").build(),HttpStatus.BAD_REQUEST);
        }
    }
}

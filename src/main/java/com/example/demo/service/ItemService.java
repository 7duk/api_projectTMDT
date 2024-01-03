package com.example.demo.service;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.item.*;
import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<List<ItemDto>> getItemsByIgId(Integer igId, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Item> items = repository.getItemsByIgIdAndIsDelete(igId, 0, size, offset);
        int count = (int) Math.ceil((double) repository.getCountItemByItemGr(igId, 0) / size);
        List<ItemDto> itemDtos = items.stream().map(e -> {
            ItemDto itemDto = mapper.map(e, ItemDto.class);
            itemDto.setItemDetails(e.getItemDetails().stream().map(i -> {
                ItemDetailDto itemDetailDto = mapper.map(i, ItemDetailDto.class);
                ColorDto color = mapper.map(i.getColor(), ColorDto.class);
                itemDetailDto.setColor(color);
                return itemDetailDto;
            }).toList());
            return itemDto;
        }).toList();
        return ResponseEntity.ok().header("X-Total-Page", String.valueOf(count)).body(itemDtos);
    }


    public ResponseEntity<List<ItemDto>> getAllItemsBySearch(String search) {
        List<Item> items = repository.getAllItemsBySearchAndIsDelete(search, 0);
        if (items != null) {
            List<ItemDto> itemDtos = items.stream().map(e -> {
                ItemDto itemDto = mapper.map(e, ItemDto.class);
                return itemDto;
            }).toList();
            return new ResponseEntity<>(itemDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<ItemSearchDto>> getItemsBySearch(String search) {
        List<Item> items = repository.getItemsBySearchAndIsDelete(search, 0);
        if (items != null) {
            List<ItemSearchDto> itemSearchDtos = items.stream().map(e -> {
                ItemSearchDto itemSearchDto = mapper.map(e, ItemSearchDto.class);
                return itemSearchDto;
            }).toList();
            return new ResponseEntity<>(itemSearchDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> saveItem(ItemSaveDto itemSaveDto) {
        try {
            Item item = mapper.map(itemSaveDto, Item.class);
            item.setLastUpdateAt(LocalDateTime.parse(itemSaveDto.getLastUpdateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            System.out.println(item.toString());
            repository.saveItem(item);
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateItem(ItemSaveDto itemSaveDto, Integer itemId) {
        Item item = mapper.map(itemSaveDto, Item.class);
        try {
            item.setLastUpdateAt(LocalDateTime.parse(itemSaveDto.getLastUpdateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            repository.updateItem(item, itemId);
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteItem(Integer itemId) {
        if (repository.deleteItem(itemId) == 1) {
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> updateDiscountItem(Integer itemId, ItemUpdateDto itemUpdateDto) {
        try{
            LocalDateTime lastUpdate = LocalDateTime.parse(itemUpdateDto.getLastUpdateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Integer discount = itemUpdateDto.getDiscount();
            if (repository.updateDiscountItem(itemId, discount, lastUpdate) != 1) {
                return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
    }

    public ResponseEntity<?> updateNameItem(Integer itemId, ItemUpdateDto itemUpdateDto) {
        try {
            LocalDateTime lastUpdate = LocalDateTime.parse(itemUpdateDto.getLastUpdateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String name = itemUpdateDto.getName();
            if (repository.updateNameItem(itemId, name, lastUpdate) != 1) {
                return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCESS"), HttpStatus.OK);
    }

    public ResponseEntity<?> updateDescriptionItem(Integer itemId, ItemUpdateDto itemUpdateDto) {
        try{
            LocalDateTime lastUpdate = LocalDateTime.parse(itemUpdateDto.getLastUpdateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String description = itemUpdateDto.getDescription();
            if (repository.updateDescriptionItem(itemId, description, lastUpdate) != 1) {
                return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
    }

    public ResponseEntity<?> getItemIdByName(String name) {
        Integer itemId = repository.getIdByName(name);
        if(itemId == null){
            return new ResponseEntity<>(Response.builder().message("ITEm ID IS NOT EXIST").build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ItemIdDto.builder().itemId(itemId).build(), HttpStatus.OK);
    }

    public ResponseEntity<?> getItemById(Integer id) {
        try{
            Optional<Item> itemOp = repository.getItemById(id);
            Item item = itemOp.orElseThrow(() -> new NullPointerException("ITEM IS NOT EXIST"));
            ItemDto itemDto = mapper.map(item, ItemDto.class);
            List<ItemDetailDto> itemDetailDtos = item.getItemDetails().stream().map(e -> {
                ItemDetailDto itemDetailDto = mapper.map(e, ItemDetailDto.class);
                ColorDto colorDto = mapper.map(e.getColor(), ColorDto.class);
                itemDetailDto.setColor(colorDto);
                return itemDetailDto;
            }).toList();
            itemDto.setItemDetails(itemDetailDtos);
            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ItemDto>> getItemByDiscounts() {
        List<Item> items = repository.getItemByDiscountsAndIsDelete(0);
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemDto> itemDtos = items.stream().map(e -> {
            ItemDto itemDto = mapper.map(e, ItemDto.class);
            itemDto.setItemDetails(e.getItemDetails().stream().map(i -> {
                ItemDetailDto itemDetailDto = mapper.map(i, ItemDetailDto.class);
                ColorDto color = mapper.map(i.getColor(), ColorDto.class);
                itemDetailDto.setColor(color);
                return itemDetailDto;
            }).toList());
            return itemDto;
        }).toList();
        return new ResponseEntity<>(itemDtos,HttpStatus.OK);
    }

    public ResponseEntity<List<ItemDto>> getItemByDiscountsNo9() {
        List<Item> items = repository.getItemByDiscountsNo9AndIsDelete(0);
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemDto> itemDtos = items.stream().map(e -> {
            ItemDto itemDto = mapper.map(e, ItemDto.class);
            itemDto.setItemDetails(e.getItemDetails().stream().map(i -> {
                ItemDetailDto itemDetailDto = mapper.map(i, ItemDetailDto.class);
                ColorDto color = mapper.map(i.getColor(), ColorDto.class);
                itemDetailDto.setColor(color);
                return itemDetailDto;
            }).toList());
            return itemDto;
        }).toList();
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<ItemDto>> getItemByDiscounts9() {
        List<Item> items = repository.getItemByDiscounts9AndIsDelete(0);
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemDto> itemDtos = items.stream().map(e -> {
            ItemDto itemDto = mapper.map(e, ItemDto.class);
            itemDto.setItemDetails(e.getItemDetails().stream().map(i -> {
                ItemDetailDto itemDetailDto = mapper.map(i, ItemDetailDto.class);
                ColorDto color = mapper.map(i.getColor(), ColorDto.class);
                itemDetailDto.setColor(color);
                return itemDetailDto;
            }).toList());
            return itemDto;
        }).toList();
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<ItemDto>> getItemNewByDay() {
        List<Item> items = repository.getItemNewByDayAndIsDelete(0);
        if(items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ItemDto> itemDtos = items.stream().map(e -> {
            ItemDto itemDto = mapper.map(e, ItemDto.class);
            itemDto.setItemDetails(e.getItemDetails().stream().map(i -> {
                ItemDetailDto itemDetailDto = mapper.map(i, ItemDetailDto.class);
                ColorDto color = mapper.map(i.getColor(), ColorDto.class);
                itemDetailDto.setColor(color);
                return itemDetailDto;
            }).toList());
            return itemDto;
        }).toList();
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }


}

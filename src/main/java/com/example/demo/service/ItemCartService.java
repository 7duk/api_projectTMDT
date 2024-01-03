package com.example.demo.service;

import com.example.demo.dto.color.ColorDto;
import com.example.demo.dto.imageitem.ImageItemDto;
import com.example.demo.dto.item.ItemForCartDto;
import com.example.demo.dto.itemcart.ItemCartDto;
import com.example.demo.dto.itemcart.ItemCartSaveDto;
import com.example.demo.dto.itemdetail.ItemDetailForCartDto;
import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.ItemCart;
import com.example.demo.repository.ItemCartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemCartService {
    private final ItemCartRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<?> saveItemInCart(ItemCartSaveDto itemCartSaveDto) {
        try {
            repository.saveItemInCart(itemCartSaveDto.getUserId(), itemCartSaveDto.getItemDetailId(), itemCartSaveDto.getQuantity());
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> getItemsInCartByUser(Integer userId) {
        List<ItemCart> itemCarts = repository.getCartsByUser(userId, 0);
        if(!itemCarts.isEmpty()){
            List<ItemCartDto> itemCartDtos = itemCarts.stream().map(e -> {
                ItemCartDto itemCartDto = new ItemCartDto();
                itemCartDto.setId(e.getId());
                itemCartDto.setUserId(e.getUserId());
                ItemDetailForCartDto itemDetailForCartDto = new ItemDetailForCartDto();
                ItemForCartDto itemForCartDto = new ItemForCartDto();
                itemForCartDto.setId(e.getItemDetail().getItems().getId());
                itemForCartDto.setName(e.getItemDetail().getItems().getName());
                itemForCartDto.setSellPrice(e.getItemDetail().getItems().getSellPrice());
                itemForCartDto.setDiscount(e.getItemDetail().getItems().getDiscount());
                itemForCartDto.setRating(e.getItemDetail().getItems().getRating());
                itemForCartDto.setDescription(e.getItemDetail().getItems().getDescription());
                itemForCartDto.setImagesItem(e.getItemDetail().getItems().getImagesItem().stream().map(element -> {
                    return mapper.map(element, ImageItemDto.class);
                }).toList());
                itemDetailForCartDto.setId(e.getItemDetail().getId());
                itemDetailForCartDto.setItem(itemForCartDto);
                ColorDto colorDto = mapper.map(e.getItemDetail().getColor(), ColorDto.class);
                itemDetailForCartDto.setColor(colorDto);
                itemCartDto.setItemDetail(itemDetailForCartDto);
                itemCartDto.setQuantity(e.getQuantity());
//                itemCartDto.setIsDelete(e.getIsDelete());
                return itemCartDto;
            }).toList();
            return new ResponseEntity<>(itemCartDtos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    public ResponseEntity<?> removeItemInCart(Integer id) {
        Integer result = repository.removeItemInCarts( id);
        if (result == 1) {
            return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> removeAllItemInCart(Integer userId) {
        repository.removeAllItemInCartsByUserID(userId);
        return new ResponseEntity<>(Response.builder().message("SUCCESS").build(), HttpStatus.OK);

    }
}

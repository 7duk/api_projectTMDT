package com.example.demo.service;

import com.example.demo.dto.itemdetail.ItemDetailDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.ItemDetail;
import com.example.demo.repository.ItemDetailDao;
import com.example.demo.repository.ItemDetailRepository;
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
public class ItemDetailService {
    private final ModelMapper mapper;
    private final ItemDetailDao dao;
    private final ItemDetailRepository repository;

    public ResponseEntity<?> saveItemDetails(List<ItemDetailDto> itemDetailDtos) {
        try{
            List<ItemDetail> itemDetails = itemDetailDtos.stream().map(e -> {
                ItemDetail itemDetail = mapper.map(e, ItemDetail.class);
                itemDetail.setColorId(e.getColorId());
                System.out.println(itemDetail.toString());
                return itemDetail;
            }).toList();
            dao.saveItemDetails(itemDetails);
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCCESS").build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateItemDetail(Integer id, ItemDetailDto itemDetailDto) {
        try {
            repository.updateAmountById(id, itemDetailDto.getAmount());
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Response.builder().message("SUCCCESS").build(), HttpStatus.OK);
    }

}

package com.example.demo.controller;

import com.example.demo.dto.itemgroup.ItemGroupDto;
import com.example.demo.service.ItemGroupService;
import com.example.demo.utils.DataTypeUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemgroups")
@RequiredArgsConstructor
public class IGroupController {
    private final ItemGroupService service;
    @PostMapping("")
    public ResponseEntity<?> saveIGroup(@RequestBody ItemGroupDto request){
        return service.saveIGroup(request);
    }
    @GetMapping("")
    public ResponseEntity<List<ItemGroupDto>> getItemGroups(){
        return service.getIGroupsIfNotDeleted(0);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeIgroup(@PathVariable("id") String igId){
        return service.removeIgById(DataTypeUtils.ConvertStringToInt(igId));
    }

}

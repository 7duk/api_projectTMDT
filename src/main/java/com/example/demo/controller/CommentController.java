package com.example.demo.controller;

import com.example.demo.dto.comment.CommentDto;
import com.example.demo.service.CommentService;
import com.example.demo.utils.DataTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService service;
    @PostMapping("")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto){
        return service.saveComment(commentDto);
    }
    @PatchMapping("/check/{cmId}")
    public ResponseEntity<?>checkComment(@PathVariable("cmId") Integer cmId){
        return service.checkComment(1,cmId);
    }
    @GetMapping("/checked/{itemId}")
    public List<CommentDto> getCommentChecked(@PathVariable("itemId")  Integer itemId,
                                              @RequestParam(name = "size", required = false, defaultValue = "0")  Integer size,
                                              @RequestParam(name = "sort", required = false, defaultValue = "DESC")  String sort){
        return service.getComment(1,itemId,size,sort);
    }
    @GetMapping("/uncheck")
    public List<CommentDto> getCommentNoCheck( @RequestParam(name="page",required = false,defaultValue = "1") Integer page,
                                               @RequestParam(name = "size", required = false, defaultValue = "5")  Integer size){
        return service.getCommentNoCheck(0,page,size);
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> removeComment(@PathVariable("id") String id){
        return service.removeComment(DataTypeUtils.ConvertStringToInt(id));
    }
}

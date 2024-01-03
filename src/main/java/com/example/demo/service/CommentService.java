package com.example.demo.service;

import com.example.demo.dto.comment.CommentDto;
import com.example.demo.dto.item.ItemUpdateDto;
import com.example.demo.dto.participant.ParticipantDto;
import com.example.demo.dto.response.Response;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final ModelMapper mapper;

    public ResponseEntity<?> saveComment(CommentDto commentDto) {
        try{
            Comment comment = mapper.map(commentDto, Comment.class);
            LocalDateTime time = LocalDateTime.parse(commentDto.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            comment.setUserId(commentDto.getUserId());
            comment.setTime(time);
            if( repository.saveComment(comment) == 1 ){
                return new ResponseEntity<>(new Response("SUCCESS"), HttpStatus.CREATED);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(new Response(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    public ResponseEntity<?> checkComment(Integer isCheck, Integer cmId) {
        return repository.checkComment(isCheck, cmId) == 1 ? new ResponseEntity<>(new Response("SUCCESS"), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<CommentDto> getComment(Integer isCheck, Integer itemId,Integer size,String sort) {
        if(size == 0){
            return repository.selectCMT(isCheck, itemId).stream().map(e -> {
                CommentDto commentDto = mapper.map(e, CommentDto.class);
                commentDto.setItemId(e.getItemId());
                commentDto.setParticipantDto(new ParticipantDto(e.getUser().getFirstName() , e.getUser().getFirstName(), e.getUser().getImage()));
                commentDto.setUserId(e.getUserId());
                return commentDto;
            }).toList();
        }
        return repository.selectCMTNEWS(isCheck, itemId,sort,size).stream().map(e -> {
                CommentDto commentDto = mapper.map(e, CommentDto.class);
                commentDto.setItemId(e.getItemId());
                commentDto.setParticipantDto(new ParticipantDto(e.getUser().getFirstName() , e.getUser().getFirstName(), e.getUser().getImage()));
                commentDto.setUserId(e.getUserId());
                return commentDto;
            }).toList();
    }

    public List<CommentDto> getCommentNoCheck(Integer isCheck,Integer page,Integer size) {
        Integer offSet = (page-1)*size;
        return repository.selectCMTNoCheck(isCheck,size,offSet).stream().map(e -> {
            CommentDto commentDto = mapper.map(e, CommentDto.class);
            commentDto.setItemId(e.getItemId());
            commentDto.setParticipantDto(new ParticipantDto(e.getUser().getFirstName() , e.getUser().getFirstName(), e.getUser().getImage()));
            commentDto.setUserId(e.getUserId());
            return commentDto;
        }).toList();
    }

    public ResponseEntity<?> removeComment(Integer id) {
        try{
            if(repository.removeComment(id)==1){
                return new ResponseEntity<>(Response.builder().message("SUCCESS").build(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(Response.builder().message("ID IS NOT EXIST").build(),HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(Response.builder().message(e.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}

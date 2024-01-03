package com.example.demo.dto.comment;

import com.example.demo.dto.item.ItemUpdateDto;
import com.example.demo.dto.participant.ParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    private String content;
    private String time;
    private Integer rating;
    private Integer userId;
    private ParticipantDto participantDto;
    private Integer itemId;
//    private ItemUpdateDto itemUpdateDto;
}

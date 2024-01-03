package com.example.demo.dto.replycomment;

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
public class ReplyCommentDto {
    private Integer id;
    private String content;
    private String time;
    private Integer userId;
    private ParticipantDto participantDto;
    private Integer commentId;
}

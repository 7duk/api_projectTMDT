package com.example.demo.dto.chat;


import com.example.demo.dto.message.MessageDto;
import com.example.demo.dto.participant.ParticipantDto;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatDto {
    private Integer id;
    private Integer participantId1;
    private Integer participantId2;
    private ParticipantDto participant;
    private MessageDto messages;
}

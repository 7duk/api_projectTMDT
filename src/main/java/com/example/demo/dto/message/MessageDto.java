package com.example.demo.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Integer id;
    private Integer senderId;
    private String message;
    private String time;
    private Integer chatId;
}

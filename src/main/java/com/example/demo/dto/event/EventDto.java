package com.example.demo.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto{
    private Integer id;
    private String title;
    private String image;
    private String description;
    private String startDate;
    private String endDate;
}

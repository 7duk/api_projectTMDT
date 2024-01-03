package com.example.demo.dto.event;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSaveDto {
    private String title;
    private String image;
    private String description;
    private String startDate;
    private String endDate;
    private Map<Integer,Integer> itemGroupIds;
    private Map<String,Double> giftCodes;
    private Integer remainingUsage;

}

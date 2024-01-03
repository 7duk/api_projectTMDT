package com.example.demo.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@Data
public class DateRangeRequest {
    private String startDate;
    private String endDate;

    public LocalDateTime getStartDateAsDateTime() {
        return LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
    }

    public LocalDateTime getEndDateAsDateTime() {
        return LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);
    }
}

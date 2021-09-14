package com.rm.springboot.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {

    Long id;
    LocalDateTime date;
    String title;
    String text;
    List<CommentsDto> comments;

    public NewsDto(Long id, LocalDateTime date, String title, String text) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
    }
}


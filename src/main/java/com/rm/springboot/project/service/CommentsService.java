package com.rm.springboot.project.service;

import com.rm.springboot.project.dto.CommentsDto;

import java.util.List;
import java.util.Optional;

public interface CommentsService {

    List<CommentsDto> findAll(Long id);

    List<CommentsDto> findAll(Long newsId, int page, int size);

    CommentsDto save(Long id, CommentsDto commentsDto);

    Optional<CommentsDto> findById(Long newsId, Long commentId);

    CommentsDto update(Long id, CommentsDto commentsDto);

    void delete(Long newsId, Long commentId);
}

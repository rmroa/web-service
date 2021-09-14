package com.rm.springboot.project.service;

import com.rm.springboot.project.dto.NewsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    List<NewsDto> findAll();

    List<NewsDto> findAll(Pageable pageable);

    Optional<NewsDto> findById(Long id);

    NewsDto save(NewsDto newsDto);

    NewsDto update(NewsDto news);

    void deleteById(Long id);
}

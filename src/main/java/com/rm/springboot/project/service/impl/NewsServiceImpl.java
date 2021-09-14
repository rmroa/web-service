package com.rm.springboot.project.service.impl;

import com.rm.springboot.project.dto.NewsDto;
import com.rm.springboot.project.entity.News;
import com.rm.springboot.project.exception.NewsNotFoundException;
import com.rm.springboot.project.mapper.NewsMapper;
import com.rm.springboot.project.repository.NewsRepository;
import com.rm.springboot.project.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.rm.springboot.project.exception.ExceptionsMessages.NEWS_NOT_FOUND;
import static java.util.stream.Collectors.toList;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;


    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    /**
     * Make a request to receive all news
     *
     * @return a list of objects news
     */
    @Transactional(readOnly = true)
    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::entityToDto)
                .collect(toList());
    }

    /**
     * find all news with pagination
     *
     * @param pageable page + amount of results
     * @return a list of objects news
     */
    @Transactional(readOnly = true)
    @Override
    public List<NewsDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .stream()
                .map(newsMapper::entityToDto)
                .collect(toList());
    }

    /**
     * Search for news by id
     *
     * @param id the parameter by which we search for news
     * @return an object news
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<NewsDto> findById(Long id) {
        return newsRepository.findById(id).map(newsMapper::entityToDto);
    }

    /**
     * Saving object news
     *
     * @param newsDto object news to save
     * @return saved news
     */
    @Transactional
    @Override
    public NewsDto save(NewsDto newsDto) {
        News news = newsRepository.save(newsMapper.dtoToEntity(newsDto));
        return newsMapper.entityToDto(news);
    }

    /**
     * Update object news
     *
     * @param newsDto object news to update
     * @return updated object news
     */
    @Transactional
    @Override
    public NewsDto update(NewsDto newsDto) {
        newsRepository.findById(newsDto.getId()).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + newsDto.getId()));
        News news = newsRepository.save(newsMapper.dtoToEntity(newsDto));
        return newsMapper.entityToDto(news);
    }

    /**
     * Delete object news by id
     *
     * @param id for delete object news
     */
    @Transactional
    @Override
    public void deleteById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + id));
        newsRepository.deleteById(news.getId());
    }
}
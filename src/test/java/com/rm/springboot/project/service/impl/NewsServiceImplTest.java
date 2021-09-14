package com.rm.springboot.project.service.impl;

import com.rm.springboot.project.dto.NewsDto;
import com.rm.springboot.project.entity.News;
import com.rm.springboot.project.mapper.NewsMapper;
import com.rm.springboot.project.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    private static final Long FIRST_NEWS_ID = 1L;
    private static final Long SECOND_NEWS_ID = 2L;
    private static final Long THIRD_NEWS_ID = 3L;
    private static final String FIRST_NEWS_TITLE = "News one title";
    private static final String SECOND_NEWS_TITLE = "News second title";
    private static final String THIRD_NEWS_TITLE = "News third title";
    private static final String NEW_NEWS_TITLE = "New news title";
    private static final String FIRST_NEWS_TEXT = "News one text";
    private static final String SECOND_NEWS_TEXT = "News second text";
    private static final String THIRD_NEWS_TEXT = "News third text";
    private static final String NEW_NEWS_TEXT = "New news text";


    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @InjectMocks
    private NewsServiceImpl newsService;

    private News news1;
    private News news2;
    private News news3;
    private News newNews;

    private NewsDto newsDto1;
    private NewsDto newsDto2;
    private NewsDto newsDto3;

    private NewsDto newNewsDto;

    private List<News> newsList;


    @BeforeEach
    void setUp() {
        news1 = new News(FIRST_NEWS_ID, LocalDateTime.now(), FIRST_NEWS_TITLE, FIRST_NEWS_TEXT);
        news2 = new News(SECOND_NEWS_ID, LocalDateTime.now(), SECOND_NEWS_TITLE, SECOND_NEWS_TEXT);
        news3 = new News(THIRD_NEWS_ID, LocalDateTime.now(), THIRD_NEWS_TITLE, THIRD_NEWS_TEXT);
        newNews = new News(THIRD_NEWS_ID, LocalDateTime.now(), NEW_NEWS_TITLE, NEW_NEWS_TEXT);


        newsDto1 = new NewsDto(FIRST_NEWS_ID, LocalDateTime.now(), FIRST_NEWS_TITLE, FIRST_NEWS_TEXT);
        newsDto2 = new NewsDto(SECOND_NEWS_ID, LocalDateTime.now(), SECOND_NEWS_TITLE, SECOND_NEWS_TEXT);
        newsDto3 = new NewsDto(THIRD_NEWS_ID, LocalDateTime.now(), THIRD_NEWS_TITLE, THIRD_NEWS_TEXT);
        newNewsDto = new NewsDto(THIRD_NEWS_ID, LocalDateTime.now(), NEW_NEWS_TITLE, NEW_NEWS_TEXT);

        newsList = Arrays.asList(news1, news2, news3);
    }


    @Test
    void testFindAll() {
        when(newsRepository.findAll()).thenReturn(newsList);
        when(newsMapper.entityToDto(news1)).thenReturn(newsDto1);
        when(newsMapper.entityToDto(news2)).thenReturn(newsDto2);
        when(newsMapper.entityToDto(news3)).thenReturn(newsDto3);
        List<NewsDto> list = newsService.findAll();
        assertThat(list).hasSize(3).extracting(NewsDto::getText).containsOnly(FIRST_NEWS_TEXT, SECOND_NEWS_TEXT, THIRD_NEWS_TEXT);
        assertThat(list).hasSize(3).extracting(NewsDto::getTitle).containsOnly(FIRST_NEWS_TITLE, SECOND_NEWS_TITLE, THIRD_NEWS_TITLE);
    }

    @Test
    void testFindAllWithPagination() {
        var pageMock = mock(Page.class);
        PageRequest pageRequest = PageRequest.of(0,2);
        when(newsMapper.entityToDto(news1)).thenReturn(newsDto1);
        when(newsMapper.entityToDto(news2)).thenReturn(newsDto2);
        when(newsMapper.entityToDto(news3)).thenReturn(newsDto3);

        when(pageMock.stream()).thenReturn(newsList.stream());
        when(newsRepository.findAll(pageRequest)).thenReturn(pageMock);

        List<NewsDto> dtoList = newsService.findAll(pageRequest);

        assertThat(dtoList).hasSize(3).extracting(NewsDto::getText).containsOnly(FIRST_NEWS_TEXT, SECOND_NEWS_TEXT, THIRD_NEWS_TEXT);
        assertThat(dtoList).hasSize(3).extracting(NewsDto::getTitle).containsOnly(FIRST_NEWS_TITLE, SECOND_NEWS_TITLE, THIRD_NEWS_TITLE);
    }


    @Test
    void testFindById() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        Optional<NewsDto> newsDto = newsService.findById(FIRST_NEWS_ID);
        verify(newsRepository, times(1)).findById(anyLong());
        assertNotNull(newsDto);
    }


    @Test
    void testCSaveNews() {
        when(newsRepository.save(newsMapper.dtoToEntity(newsDto1))).thenReturn(news1);
        newsService.save(newsMapper.entityToDto(news1));
        verify(newsRepository, times(1)).save(newsMapper.dtoToEntity(newsDto1));
    }

    @Test
    void testUpdateNews() {
        when(newsRepository.findById(THIRD_NEWS_ID)).thenReturn(Optional.of(news3));
        when(newsMapper.entityToDto(newNews)).thenReturn(newNewsDto);
        when(newsRepository.save(newsMapper.dtoToEntity(newNewsDto))).thenReturn(newNews);
        newsService.update(newsMapper.entityToDto(newNews));
        verify(newsRepository, times(1)).save(newsMapper.dtoToEntity(newNewsDto));

    }

    @Test
    void testDeleteNews() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        newsService.deleteById(news1.getId());
        verify(newsRepository, times(1)).deleteById(FIRST_NEWS_ID);

    }
}

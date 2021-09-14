package com.rm.springboot.project.service.impl;

import com.rm.springboot.project.dto.CommentsDto;
import com.rm.springboot.project.entity.Comments;
import com.rm.springboot.project.entity.News;
import com.rm.springboot.project.mapper.CommentsMapper;
import com.rm.springboot.project.repository.CommentsRepository;
import com.rm.springboot.project.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceImplTest {

    private static final Long FIRST_COMMENT_ID = 1L;
    private static final Long SECOND_COMMENT_ID = 2L;
    private static final Long THIRD_COMMENT_ID = 3L;
    private static final String FIRST_COMMENT_TEXT = "One comment Text";
    private static final String SECOND_COMMENT_TEXT = "Second comment Text";
    private static final String THIRD__COMMENT_TEXT = "Third comment Text";
    private static final String FIRST_COMMENT_USER = "Ivan";
    private static final String SECOND_COMMENT_USER = "Aleksandr";
    private static final String THIRD_COMMENT_USER = "Petr";
    private static final Long FIRST_NEWS_ID = 1L;
    private static final String FIRST_NEWS_TITLE = "News one title";
    private static final String FIRST_NEWS_TEXT = "News one text";

    @Mock
    private CommentsRepository commentRepository;

    @Mock
    private CommentsMapper commentsMapper;

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    CommentsServiceImpl commentService;

    private Comments comment1;
    private Comments comment2;
    private Comments comment3;
    private Comments newComment;

    private News news1;

    private CommentsDto commentDto1;
    private CommentsDto commentDto2;
    private CommentsDto commentDto3;
    private CommentsDto newCommentDto;

    private List<Comments> listComment;

    @BeforeEach
    void setUp() {
        comment1 = new Comments(FIRST_COMMENT_ID, LocalDateTime.now(), FIRST_COMMENT_TEXT, FIRST_COMMENT_USER);
        comment2 = new Comments(SECOND_COMMENT_ID, LocalDateTime.now(), SECOND_COMMENT_TEXT, SECOND_COMMENT_USER);
        comment3 = new Comments(THIRD_COMMENT_ID, LocalDateTime.now(), THIRD__COMMENT_TEXT, THIRD_COMMENT_USER);
        newComment = new Comments(FIRST_COMMENT_ID, LocalDateTime.now(), FIRST_COMMENT_TEXT, FIRST_COMMENT_USER);

        news1 = new News(FIRST_NEWS_ID, LocalDateTime.now(), FIRST_NEWS_TITLE, FIRST_NEWS_TEXT);

        commentDto1 = new CommentsDto(FIRST_COMMENT_ID, LocalDateTime.now(), FIRST_COMMENT_TEXT, FIRST_COMMENT_USER);
        commentDto2 = new CommentsDto(SECOND_COMMENT_ID, LocalDateTime.now(), SECOND_COMMENT_TEXT, SECOND_COMMENT_USER);
        commentDto3 = new CommentsDto(THIRD_COMMENT_ID, LocalDateTime.now(), THIRD__COMMENT_TEXT, THIRD_COMMENT_USER);
        newCommentDto = new CommentsDto(FIRST_COMMENT_ID, LocalDateTime.now(), FIRST_COMMENT_TEXT, FIRST_COMMENT_USER);

        listComment = Arrays.asList(comment1, comment2, comment3);
    }

    @Test
    void testFindAllComments() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        when(commentRepository.findByNewsId(FIRST_NEWS_ID)).thenReturn(listComment);
        when(commentsMapper.entityToDto(comment1)).thenReturn(commentDto1);
        when(commentsMapper.entityToDto(comment2)).thenReturn(commentDto2);
        when(commentsMapper.entityToDto(comment3)).thenReturn(commentDto3);

        List<CommentsDto> list = commentService.findAll(FIRST_NEWS_ID);
        assertThat(list).hasSize(3).extracting(CommentsDto::getText).containsOnly(FIRST_COMMENT_TEXT, SECOND_COMMENT_TEXT, THIRD__COMMENT_TEXT);
        assertThat(list).hasSize(3).extracting(CommentsDto::getUserName).containsOnly(FIRST_COMMENT_USER, SECOND_COMMENT_USER, THIRD_COMMENT_USER);
    }

    @Test
    void testFindAllCommentsWithPagination() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(DESC, "date"));
        when(commentsMapper.entityToDto(comment1)).thenReturn(commentDto1);
        when(commentsMapper.entityToDto(comment2)).thenReturn(commentDto2);
        when(commentsMapper.entityToDto(comment3)).thenReturn(commentDto3);
        when(commentRepository.findByNewsId(FIRST_NEWS_ID, pageRequest)).thenReturn(listComment);

        List<CommentsDto> listCommentsDto = commentService.findAll(FIRST_NEWS_ID, 0, 2);
        assertThat(listCommentsDto).hasSize(3).extracting(CommentsDto::getText).containsOnly(FIRST_COMMENT_TEXT, SECOND_COMMENT_TEXT, THIRD__COMMENT_TEXT);
        assertThat(listCommentsDto).hasSize(3).extracting(CommentsDto::getUserName).containsOnly(FIRST_COMMENT_USER, SECOND_COMMENT_USER, THIRD_COMMENT_USER);
    }

    @Test
    void testFindByIdComments() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        Optional<CommentsDto> commentDto = commentService.findById(FIRST_NEWS_ID, FIRST_COMMENT_ID);
        verify(commentRepository, times(1)).findByNewsIdAndId(FIRST_NEWS_ID, FIRST_COMMENT_ID);
        assertNotNull(commentDto);
    }

    @Test
    void testSaveComments() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        when(commentsMapper.dtoToEntity(newCommentDto)).thenReturn(newComment);
        when(commentsMapper.entityToDto(newComment)).thenReturn(newCommentDto);
        newComment.setNews(news1);
        commentService.save(FIRST_NEWS_ID, commentsMapper.entityToDto(newComment));
        verify(commentRepository, times(1)).save(commentsMapper.dtoToEntity(newCommentDto));
    }

    @Test
    void testUpdate() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        when(commentRepository.findById(FIRST_COMMENT_ID)).thenReturn(Optional.of(comment1));
        when(commentsMapper.dtoToEntity(newCommentDto)).thenReturn(newComment);
        when(commentsMapper.entityToDto(newComment)).thenReturn(newCommentDto);
        newComment.setNews(news1);
        when(commentRepository.save(newComment)).thenReturn(newComment);
        commentService.update(FIRST_NEWS_ID, commentsMapper.entityToDto(newComment));
        verify(commentRepository, times(1)).save(commentsMapper.dtoToEntity(newCommentDto));
    }

    @Test
    void testDeleteComments() {
        when(newsRepository.findById(FIRST_NEWS_ID)).thenReturn(Optional.of(news1));
        when(commentRepository.findById(FIRST_COMMENT_ID)).thenReturn(Optional.of(comment1));
        commentService.delete(FIRST_NEWS_ID, FIRST_COMMENT_ID);
        verify(commentRepository, times(1)).deleteByNewsIdAndId(FIRST_NEWS_ID, FIRST_COMMENT_ID);
    }
}
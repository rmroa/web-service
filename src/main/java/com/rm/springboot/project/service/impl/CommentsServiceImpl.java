package com.rm.springboot.project.service.impl;

import com.rm.springboot.project.dto.CommentsDto;
import com.rm.springboot.project.entity.Comments;
import com.rm.springboot.project.entity.News;
import com.rm.springboot.project.exception.CommentsNotFoundException;
import com.rm.springboot.project.exception.NewsNotFoundException;
import com.rm.springboot.project.mapper.CommentsMapper;
import com.rm.springboot.project.repository.CommentsRepository;
import com.rm.springboot.project.repository.NewsRepository;
import com.rm.springboot.project.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rm.springboot.project.exception.ExceptionsMessages.COMMENTS_NOT_FOUND;
import static com.rm.springboot.project.exception.ExceptionsMessages.NEWS_NOT_FOUND;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    private final NewsRepository newsRepository;

    private final CommentsMapper commentsMapper;

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository, NewsRepository newsRepository,
                               CommentsMapper commentsMapper) {
        this.commentsRepository = commentsRepository;
        this.newsRepository = newsRepository;
        this.commentsMapper = commentsMapper;
    }

    /**
     * Make a request to receive all comments
     *
     * @param id for check news
     * @return a list of comments
     */
    @Transactional(readOnly = true)
    @Override
    public List<CommentsDto> findAll(Long id) {
        newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + id));
        return commentsRepository.findByNewsId(id)
                .stream()
                .map(commentsMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Find all comments with pagination
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @return a list of objects comments
     */
    @Transactional(readOnly = true)
    @Override
    public List<CommentsDto> findAll(Long newsId, int page, int size) {
        newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + newsId));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(DESC, "date"));
        return commentsRepository.findByNewsId(newsId, pageRequest)
                .stream()
                .map(commentsMapper::entityToDto)
                .collect(toList());
    }

    /**
     * Search for comments by id
     *
     * @param newsId    for check news and search for comments
     * @param commentId the parameter by which we search for comments
     * @return a comments object
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<CommentsDto> findById(Long newsId, Long commentId) {
        newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + newsId));
        return commentsRepository.findByNewsIdAndId(newsId, commentId)
                .map(commentsMapper::entityToDto);
    }

    /**
     * Saving object comments
     *
     * @param id          for check news
     * @param commentsDto commentsDto object comments to save
     * @return saved comments
     */
    @Transactional
    @Override
    public CommentsDto save(Long id, CommentsDto commentsDto) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + id));
        Comments comments = commentsMapper.dtoToEntity(commentsDto);
        comments.setNews(news);
        Comments saveComments = commentsRepository.save(comments);
        return commentsMapper.entityToDto(saveComments);
    }

    /**
     * Update object comments
     *
     * @param id          for check news
     * @param commentsDto object comments to update
     * @return updated object comments
     */
    @Transactional
    @Override
    public CommentsDto update(Long id, CommentsDto commentsDto) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + id));
        commentsRepository.findById(commentsDto.getId()).orElseThrow(() -> new CommentsNotFoundException(COMMENTS_NOT_FOUND + commentsDto.getId()));
        Comments comments = commentsMapper.dtoToEntity(commentsDto);
        comments.setNews(news);
        Comments saveComments = commentsRepository.save(comments);
        return commentsMapper.entityToDto(saveComments);
    }

    /**
     * Delete object comments by id
     *
     * @param newsId    for check news
     * @param commentId for check comment and delete object comments
     */
    @Transactional
    @Override
    public void delete(Long newsId, Long commentId) {
        newsRepository.findById(newsId).orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND + newsId));
        commentsRepository.findById(commentId).orElseThrow(() -> new CommentsNotFoundException(COMMENTS_NOT_FOUND + commentId));
        commentsRepository.deleteByNewsIdAndId(newsId, commentId);
    }
}

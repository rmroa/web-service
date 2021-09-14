package com.rm.springboot.project.controller;

import com.rm.springboot.project.dto.NewsDto;
import com.rm.springboot.project.service.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.rm.springboot.project.utils.AppConstants.DEFAULT_PAGE_NUMBER;
import static com.rm.springboot.project.utils.AppConstants.DEFAULT_PAGE_SIZE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RequestMapping("/news")
@RestController
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Make a request to receive all news
     *
     * @return a list of objects news, if any, otherwise an empty list
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "News has been successfully found"),
            @ApiResponse(code = 404, message = "News not found")
    })
    @ApiOperation("Search all news")
    @GetMapping
    public ResponseEntity<List<NewsDto>> findAll() {
        return new ResponseEntity<>(newsService.findAll(), HttpStatus.OK);
    }

    /**
     * Make a request to receive all news with pagination
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @return a list of objects news with pagination, if any, otherwise an empty list
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "News has been successfully found"),
            @ApiResponse(code = 404, message = "News not found")
    })
    @ApiOperation("Search news with pagination")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<NewsDto>> findAllNews(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                                     @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(DESC, "date"));
        return ResponseEntity.ok(newsService.findAll(pageRequest));
    }

    /**
     * Search for news by id
     *
     * @param id the parameter by which we search for news
     * @return an object news if any, otherwise HttpStatus.NOT_FOUND)
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "News has been successfully found"),
            @ApiResponse(code = 404, message = "News not found")
    })
    @ApiOperation("Search news by id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<NewsDto>> findNewsById(@PathVariable Long id) {
        Optional<NewsDto> newsDtoById = newsService.findById(id);
        return newsDtoById.map(newsDto -> new ResponseEntity<>(newsDtoById, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Saving object news
     *
     * @param newsDto object news to save
     * @return saved news with HttpStatus.CREATED
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = "News has been successfully created"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Saving news")
    @PostMapping
    public ResponseEntity<NewsDto> saveNews(@RequestBody NewsDto newsDto) {
        return new ResponseEntity<>(newsService.save(newsDto), HttpStatus.CREATED);
    }

    /**
     * Update object news
     *
     * @param newsDto object news to update
     * @return updated object news
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = "News has been updated"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Update news")
    @PutMapping
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto newsDto) {
        return new ResponseEntity<>(newsService.update(newsDto), HttpStatus.OK);
    }

    /**
     * Delete object news by id
     *
     * @param id for delete object news
     * @return HttpStatus.NO_CONTENT on successful deletion
     */
    @ApiResponses({
            @ApiResponse(code = 204, message = "News has been deleted, no content"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Deletion news by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<NewsDto> deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

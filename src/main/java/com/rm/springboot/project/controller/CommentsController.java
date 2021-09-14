package com.rm.springboot.project.controller;

import com.rm.springboot.project.dto.CommentsDto;
import com.rm.springboot.project.service.CommentsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@RestController
@RequestMapping("/news/{newsId}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * Make a request to receive all comments
     *
     * @param newsId for check news
     * @return a list of objects comments, if any, otherwise an empty list
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comments has been successfully found"),
            @ApiResponse(code = 404, message = "Comments not found")
    })
    @ApiOperation("Search all comments")
    @GetMapping
    public ResponseEntity<List<CommentsDto>> findAllComments(@PathVariable Long newsId) {
        List<CommentsDto> listCommentsDto = commentsService.findAll(newsId);
        return new ResponseEntity<>(listCommentsDto, HttpStatus.OK);
    }

    /**
     * Make a request to receive all comments with pagination
     *
     * @param newsId for check news
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @return a list of objects comments, if any, otherwise an empty list
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comments has been successfully found"),
            @ApiResponse(code = 404, message = "Comments not found")
    })
    @ApiOperation("Search all comments with pagination")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<CommentsDto>> getAllComments(@PathVariable Long newsId,
                                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
                                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(commentsService.findAll(newsId, page, size));
    }

    /**
     * Search for comments by id
     *
     * @param newsId     for check news and search for comments
     * @param commentsId the parameter by which we search for comments
     * @return a comments object if any, otherwise HttpStatus.NOT_FOUND)
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "Comment has been successfully found"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    @ApiOperation("Search comments by id")
    @GetMapping("/{commentsId}")
    public ResponseEntity<Optional<CommentsDto>> findCommentById(@PathVariable Long newsId,
                                                                 @PathVariable Long commentsId) {
        Optional<CommentsDto> commentDtoById = commentsService.findById(newsId, commentsId);
        return commentDtoById.map(commentDto -> new ResponseEntity<>(commentDtoById, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Saving object comments
     *
     * @param newsId      for check news
     * @param commentsDto object comments to save
     * @return saved comments with HttpStatus.CREATED
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = "Comment has been successfully created"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Saving comments")
    @PostMapping
    public ResponseEntity<CommentsDto> saveComment(@PathVariable Long newsId,
                                                   @RequestBody CommentsDto commentsDto) {
        return new ResponseEntity<>(commentsService.save(newsId, commentsDto), HttpStatus.CREATED);
    }

    /**
     * Update object comments
     *
     * @param newsId      for check news
     * @param commentsDto object comments to update
     * @return updated object comments
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = "Comment has been updated"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Update comments")
    @PutMapping
    public ResponseEntity<CommentsDto> updateComment(@PathVariable Long newsId,
                                                     @RequestBody CommentsDto commentsDto) {
        return new ResponseEntity<>(commentsService.update(newsId, commentsDto), HttpStatus.OK);
    }

    /**
     * Delete object comments by id
     *
     * @param newsId     for check news
     * @param commentsId for check comment and delete object comments
     * @return HttpStatus.NO_CONTENT on successful deletion
     */
    @ApiResponses({
            @ApiResponse(code = 204, message = "Comment has been deleted, no content"),
            @ApiResponse(code = 400, message = "Incorrect data")
    })
    @ApiOperation("Deletion comments by id")
    @DeleteMapping("/{commentsId}")
    public ResponseEntity<CommentsDto> deleteComment(@PathVariable Long newsId,
                                                     @PathVariable Long commentsId) {
        commentsService.delete(newsId, commentsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

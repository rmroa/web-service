package com.rm.springboot.project.mapper;

import com.rm.springboot.project.dto.CommentsDto;
import com.rm.springboot.project.entity.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CommentsMapper {

    CommentsMapper INSTANCE = Mappers.getMapper(CommentsMapper.class);

    /**
     * converting object Comments to object CommentsDto
     *
     * @param comments convertible object
     * @return mapped object
     */
    CommentsDto entityToDto(Comments comments);

    /**
     * converting object CommentsDto to object Comments
     *
     * @param commentsDto convertible object
     * @return mapped object
     */
    Comments dtoToEntity(CommentsDto commentsDto);
}

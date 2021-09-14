package com.rm.springboot.project.mapper;

import com.rm.springboot.project.dto.NewsDto;
import com.rm.springboot.project.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    /**
     * converting object News to object NewsDto
     *
     * @param news convertible object
     * @return mapped object
     */
    NewsDto entityToDto(News news);

    /**
     * converting object NewsDto to object News
     *
     * @param newsDto convertible object
     * @return mapped object
     */
    News dtoToEntity(NewsDto newsDto);
}

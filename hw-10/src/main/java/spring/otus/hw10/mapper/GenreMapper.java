package spring.otus.hw10.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.otus.hw10.dto.GenreCreateDto;
import spring.otus.hw10.dto.GenreDto;
import spring.otus.hw10.models.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);

    Genre toModel(GenreDto genre);

    @Mapping(target = "id", ignore = true)
    Genre toModel(GenreCreateDto genre);
}

package spring.otus.hw11.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.otus.hw11.dto.GenreCreateDto;
import spring.otus.hw11.dto.GenreDto;
import spring.otus.hw11.models.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);

    Genre toModel(GenreDto genre);

    @Mapping(target = "id", ignore = true)
    Genre toModel(GenreCreateDto genre);
}

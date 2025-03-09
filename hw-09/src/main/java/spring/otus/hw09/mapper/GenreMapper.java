package spring.otus.hw09.mapper;

import org.mapstruct.Mapper;
import spring.otus.hw09.dto.GenreDto;
import spring.otus.hw09.models.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);

    Genre toModel(GenreDto genre);
}

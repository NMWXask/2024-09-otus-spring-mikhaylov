package spring.otus.hw11.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.otus.hw11.dto.AuthorCreateDto;
import spring.otus.hw11.dto.AuthorDto;
import spring.otus.hw11.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toModel(AuthorDto author);

    @Mapping(target = "id", ignore = true)
    Author toModel(AuthorCreateDto author);
}

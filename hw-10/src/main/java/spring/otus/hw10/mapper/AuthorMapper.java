package spring.otus.hw10.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.otus.hw10.dto.AuthorCreateDto;
import spring.otus.hw10.dto.AuthorDto;
import spring.otus.hw10.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toModel(AuthorDto author);

    @Mapping(target = "id", ignore = true)
    Author toModel(AuthorCreateDto author);
}

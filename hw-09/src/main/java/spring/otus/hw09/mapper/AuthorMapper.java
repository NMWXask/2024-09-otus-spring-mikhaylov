package spring.otus.hw09.mapper;

import org.mapstruct.Mapper;
import spring.otus.hw09.dto.AuthorDto;
import spring.otus.hw09.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author toModel(AuthorDto author);
}

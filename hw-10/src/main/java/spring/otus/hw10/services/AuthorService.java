package spring.otus.hw10.services;


import spring.otus.hw10.dto.AuthorCreateDto;
import spring.otus.hw10.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto update(AuthorDto author);

    AuthorDto create(AuthorCreateDto author);

    void deleteById(long id);
}

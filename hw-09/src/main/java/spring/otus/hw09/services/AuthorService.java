package spring.otus.hw09.services;


import spring.otus.hw09.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto update(AuthorDto author);

    AuthorDto create(AuthorDto author);

    void deleteById(long id);
}

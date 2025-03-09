package spring.otus.hw10.services;


import spring.otus.hw10.dto.GenreCreateDto;
import spring.otus.hw10.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long id);

    GenreDto create(GenreCreateDto genre);

    GenreDto update(GenreDto genre);

    void deleteById(long id);
}

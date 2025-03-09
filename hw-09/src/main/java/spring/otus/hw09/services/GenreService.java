package spring.otus.hw09.services;


import spring.otus.hw09.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long id);

    GenreDto create(GenreDto genre);

    GenreDto update(GenreDto genre);


    void deleteById(long id);
}

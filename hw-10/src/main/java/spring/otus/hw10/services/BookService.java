package spring.otus.hw10.services;


import spring.otus.hw10.dto.BookCreateDto;
import spring.otus.hw10.dto.BookDto;
import spring.otus.hw10.dto.BookFullDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookFullDto> findAll();

    BookDto create(BookCreateDto book);

    BookDto update(BookDto book);

    void deleteById(long id);

}

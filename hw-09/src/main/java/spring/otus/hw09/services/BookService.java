package spring.otus.hw09.services;

import spring.otus.hw09.dto.BookCreateDto;
import spring.otus.hw09.dto.BookDto;
import spring.otus.hw09.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookUpdateDto findById(long id);

    List<BookDto> findAll();

    BookUpdateDto create(BookCreateDto book);

    BookUpdateDto update(BookUpdateDto book);

    void deleteById(long id);

}

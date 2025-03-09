package spring.otus.hw10.services;


import spring.otus.hw10.dto.CommentCreateDto;
import spring.otus.hw10.dto.CommentDto;
import spring.otus.hw10.dto.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    public List<CommentDto> findAllByBook(Long bookId);

    public CommentDto findById(long id);

    public void deleteById(long id);

    public CommentDto create(CommentCreateDto comment);

    public CommentDto update(CommentUpdateDto comment);

    public void deleteByBookId(long bookId);

}

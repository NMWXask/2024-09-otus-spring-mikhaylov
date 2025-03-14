package spring.otus.hw10.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw10.dto.CommentCreateDto;
import spring.otus.hw10.dto.CommentDto;
import spring.otus.hw10.dto.CommentUpdateDto;
import spring.otus.hw10.exceptions.NotFoundException;
import spring.otus.hw10.mapper.CommentMapper;
import spring.otus.hw10.models.Book;
import spring.otus.hw10.models.Comment;
import spring.otus.hw10.repositories.BookRepository;
import spring.otus.hw10.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;


    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBook(Long bookId) {
        return commentRepository.findAllByBookId(bookId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        return commentMapper.toDto(commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(id))
        ));
    }


    @Override
    @Transactional
    public void deleteById(long id) throws NotFoundException {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByBookId(long bookId) throws NotFoundException {
        commentRepository.deleteByBookId(bookId);
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto commentDto) throws NotFoundException {
        Book book = bookRepository.findById(commentDto.bookId())
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(commentDto.bookId()))
        );
        return commentMapper.toDto(commentRepository.save(commentMapper.toModel(commentDto,book)));

    }


    @Override
    @Transactional
    public CommentDto update(CommentUpdateDto commentDto) throws NotFoundException {
        Comment comment = commentRepository.findById(commentDto.id())
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(commentDto.id()))
            );
            comment.setText(commentDto.text());
        return commentMapper.toDto(commentRepository.save(comment));
    }

}

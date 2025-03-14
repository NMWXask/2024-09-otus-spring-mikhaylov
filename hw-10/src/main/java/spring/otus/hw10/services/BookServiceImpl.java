package spring.otus.hw10.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw10.dto.BookCreateDto;
import spring.otus.hw10.dto.BookDto;
import spring.otus.hw10.dto.BookFullDto;
import spring.otus.hw10.exceptions.NotFoundException;
import spring.otus.hw10.mapper.BookMapper;
import spring.otus.hw10.models.Book;
import spring.otus.hw10.repositories.AuthorRepository;
import spring.otus.hw10.repositories.BookRepository;
import spring.otus.hw10.repositories.CommentRepository;
import spring.otus.hw10.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {
        return bookMapper.toEditDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id))
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookFullDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }




    @Override
    @Transactional
    public BookDto create(BookCreateDto bookDto) {
        var author = authorRepository.findById(bookDto.authorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(bookDto.authorId())));
        var genre = genreRepository.findById(bookDto.genreId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(bookDto.genreId())));

        var book = bookMapper.toModel(bookDto, author, genre);
        return bookMapper.toEditDto(bookRepository.save(book));

    }

    @Override
    @Transactional
    public BookDto update(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.id())
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookDto.id())));

        var author = authorRepository.findById(bookDto.authorId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(bookDto.authorId())));
        var genre = genreRepository.findById(bookDto.genreId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(bookDto.genreId())));

        book.setTitle(bookDto.title());
        book.setAuthor(author);
        book.setGenre(genre);

        return bookMapper.toEditDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }
}

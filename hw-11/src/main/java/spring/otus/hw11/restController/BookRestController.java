package spring.otus.hw11.restController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.otus.hw11.dto.BookCreateDto;
import spring.otus.hw11.dto.BookDto;
import spring.otus.hw11.dto.BookFullDto;
import spring.otus.hw11.exceptions.NotFoundException;
import spring.otus.hw11.mapper.BookMapper;
import spring.otus.hw11.models.Book;
import spring.otus.hw11.repositories.AuthorRepository;
import spring.otus.hw11.repositories.BookRepository;
import spring.otus.hw11.repositories.GenreRepository;


@RequiredArgsConstructor
@RestController
public class BookRestController {



    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final BookMapper bookMapper;

    @GetMapping("/api/books")
    public Flux<BookFullDto> getAllBooks() {
        return bookRepository.findAllBooks()
                .map(bookMapper::toDto);
    }

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable("id") long id) {
        return bookRepository.findByBookId(id)
                .map(bookMapper::toEditDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/api/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") long id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/api/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> createBook(@Valid @RequestBody BookCreateDto bookDto) {
        return genreRepository.findById(bookDto.genreId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Genre with id %d not found".formatted(bookDto.genreId()))
                ))
                .zipWith(authorRepository.findById(bookDto.authorId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                                "Author with id %d not found".formatted(bookDto.authorId()))
                        )))
                .flatMap(tuple -> {
                      return bookRepository.saveBook(new Book(bookDto.title(), tuple.getT2(), tuple.getT1()))
                            .map(bookMapper::toEditDto);
                });
    }

    @PutMapping("/api/books/{id}")
    public Mono<BookDto> updateBook(@PathVariable("id") long id,
                              @Valid @RequestBody BookDto bookDto) {

        return genreRepository.findById(bookDto.genreId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Genre with id %d not found".formatted(bookDto.genreId()))
                ))
                .zipWith(authorRepository.findById(bookDto.authorId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Author with id %d not found".formatted(bookDto.authorId()))
                        )))
                .flatMap(tuple -> bookRepository.findById(bookDto.id())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Book with id %d not found".formatted(bookDto.id()))
                        ))
                        .flatMap(book -> {
                            book.setGenre(tuple.getT1());
                            book.setTitle(bookDto.title());
                            book.setAuthor(tuple.getT2());
                            return bookRepository.saveBook(book)
                                    .map(bookMapper::toEditDto);
                        }));

    }
}

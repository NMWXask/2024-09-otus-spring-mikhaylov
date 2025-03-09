package spring.otus.hw11;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.otus.hw11.dto.BookCreateDto;
import spring.otus.hw11.dto.BookDto;
import spring.otus.hw11.dto.BookFullDto;
import spring.otus.hw11.mapper.BookMapper;
import spring.otus.hw11.models.Author;
import spring.otus.hw11.models.Book;
import spring.otus.hw11.models.Genre;
import spring.otus.hw11.repositories.AuthorRepository;
import spring.otus.hw11.repositories.BookRepository;
import spring.otus.hw11.repositories.GenreRepository;
import spring.otus.hw11.restController.BookRestController;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@WebFluxTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookMapper bookMapper;


    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(
                new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"), new Genre(1L, "genre1")),
                new Book(2L, "BookTitle2", new Author(2L, "AuthorName2"), new Genre(2L, "genre2"))
        );

        List<BookFullDto> bookDtos = List.of(
                new BookFullDto(1L, "BookTitle1", "AuthorName1", "genre1"),
                new BookFullDto(2L, "BookTitle2", "AuthorName2", "genre2")
        );

        Flux<Book> booksFlux = Flux.fromIterable(books);

        Mockito.when(bookRepository.findAllBooks()).thenReturn(booksFlux);
        Mockito.when(bookMapper.toDto(books.get(0))).thenReturn(bookDtos.get(0));
        Mockito.when(bookMapper.toDto(books.get(1))).thenReturn(bookDtos.get(1));

        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookFullDto.class).isEqualTo(bookDtos);
    }

    @Test
    public void shouldDeleteBook() {
        Mono<Void> voidReturn = Mono.empty();

        Mockito.when(bookRepository.deleteById(1L))
                .thenReturn(voidReturn);

        webTestClient.delete().uri("/api/books/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void shouldCreateBook() {
        String title = "BookTitle1";

        Genre genre = new Genre(1L, "genre1");
        Mockito.when(genreRepository.findById(1L)).thenReturn(Mono.just(genre));

        Author author = new Author(1L, "AuthorName1");
        Mockito.when(authorRepository.findById(1L)).thenReturn(Mono.just(author));

        Book book = new Book(1L, title, author, genre);
        BookDto bookDto = new BookDto(1L, title, 1L, 1L);
        Mockito.when(bookRepository.saveBook(any(Book.class))).thenReturn(Mono.just(book));
        Mockito.when(bookMapper.toEditDto(book)).thenReturn(bookDto);

        webTestClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BookCreateDto(title, 1L, 1L)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class).isEqualTo(bookDto);
    }

    @Test
    public void shouldEditBook() {
        String title = "BookTitle1";

        Genre genre = new Genre(1L, "genre1");
        Mockito.when(genreRepository.findById(1L)).thenReturn(Mono.just(genre));

        Author author = new Author(1L, "AuthorName1");
        Mockito.when(authorRepository.findById(1L)).thenReturn(Mono.just(author));

        Book book = new Book(1L, title, author, genre);
        BookDto bookDto = new BookDto(1L, title, 1L, 1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Mono.just(book));
        Mockito.when(bookRepository.saveBook(any(Book.class))).thenReturn(Mono.just(book));
        Mockito.when(bookMapper.toEditDto(book)).thenReturn(bookDto);

        webTestClient.put()
                .uri("/api/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new BookDto(1L, title, 1L, 1L)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class).isEqualTo(bookDto);

        Mockito.verify(bookRepository, times(1)).saveBook(book);
    }
}

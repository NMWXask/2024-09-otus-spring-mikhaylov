package spring.otus.hw11.restController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.otus.hw11.dto.CommentCreateDto;
import spring.otus.hw11.dto.CommentDto;
import spring.otus.hw11.dto.CommentUpdateDto;
import spring.otus.hw11.exceptions.NotFoundException;
import spring.otus.hw11.mapper.CommentMapper;
import spring.otus.hw11.repositories.BookRepository;
import spring.otus.hw11.repositories.CommentRepository;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private final CommentMapper commentMapper;


    @GetMapping("/api/comments")
    public Flux<CommentDto> getAllComments(@RequestParam("bookId") long bookId) {
        return commentRepository.findAllByBookId(bookId)
                .map(commentMapper::toDto);
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteComment(@PathVariable("id") long id) {
        return commentRepository.deleteById(id);
    }

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CommentDto> createComment(@Valid @RequestBody CommentCreateDto comment) {

        return bookRepository.findById(comment.bookId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Comment with id %d not found".formatted(comment.bookId()))
                        ))
                        .flatMap(book -> {
                              return commentRepository.save(commentMapper.toModel(comment))
                                    .map(commentMapper::toDto);
                        });
    }

    @PutMapping("/api/comments/{id}")
    public Mono<CommentDto> updateComment(@PathVariable("id") long id,
                                           @Valid @RequestBody CommentUpdateDto comment) {
          return commentRepository.findById(id)
                  .switchIfEmpty(Mono.error(() ->
                          new NotFoundException("Comment with id %d not found".formatted(id))))
                  .flatMap(selectedComment -> commentRepository.save(
                          commentMapper.toModel(comment)
                          )
                       .map(commentMapper::toDto));
    }
}

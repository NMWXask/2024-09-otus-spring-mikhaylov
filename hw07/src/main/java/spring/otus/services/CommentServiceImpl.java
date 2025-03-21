package spring.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.exceptions.EntityNotFoundException;
import spring.otus.models.Book;
import spring.otus.models.Comment;
import spring.otus.repositories.BookRepository;
import spring.otus.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    private Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByBook(Long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }


    @Override
    @Transactional
    public void deleteById(long id) throws EntityNotFoundException {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByBookId(long bookId) throws EntityNotFoundException {
        commentRepository.deleteByBookId(bookId);
    }

    @Override
    @Transactional
    public Comment create(long bookId, String text) throws EntityNotFoundException {
        Comment comment = new Comment();
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(bookId))
        );
        comment.setText(text);
        comment.setBook(book);
        return save(comment);
    }


    @Override
    @Transactional
    public Comment update(long id, String text) throws EntityNotFoundException {
        Comment comment = findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(id))
        );
        comment.setText(text);
        return save(comment);
    }


}

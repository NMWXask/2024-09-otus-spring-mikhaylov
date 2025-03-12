package spring.otus.hw08.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.otus.hw08.exceptions.EntityNotFoundException;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.models.Comment;
import spring.otus.hw08.repositories.BookRepository;
import spring.otus.hw08.repositories.CommentRepository;
import spring.otus.hw08.services.CommentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Book Title", null, null);
        comment = new Comment("Comment Text", book);
        comment.setId("1");
    }

    @Test
    void testFindAllByBookWhenCommentsExistThenReturnListOfComments() {
        List<Comment> comments = List.of(comment);
        when(commentRepository.findAllByBookId("1")).thenReturn(comments);

        List<Comment> foundComments = commentService.findAllByBook("1");

        assertThat(foundComments).isNotEmpty();
        assertThat(foundComments).hasSize(1);
        assertThat(foundComments).contains(comment);
    }

    @Test
    void testFindByIdWhenCommentExistsThenReturnComment() {
        when(commentRepository.findById("1")).thenReturn(Optional.of(comment));

        Optional<Comment> foundComment = commentService.findById("1");

        assertThat(foundComment).isPresent();
        assertThat(foundComment.get()).isEqualTo(comment);
    }

    @Test
    void testFindByIdWhenCommentDoesNotExistThenReturnEmpty() {
        when(commentRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Comment> foundComment = commentService.findById("1");

        assertThat(foundComment).isNotPresent();
    }

    @Test
    void testCreateWhenValidDataThenReturnCreatedComment() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment createdComment = commentService.create("1", "Comment Text");

        assertThat(createdComment).isEqualTo(comment);
    }

    @Test
    void testCreateWhenBookDoesNotExistThenThrowEntityNotFoundException() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.create("1", "Comment Text"));
    }

    @Test
    void testUpdateWhenValidDataThenReturnUpdatedComment() {
        when(commentRepository.findById("1")).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment updatedComment = commentService.update("1", "Updated Text");

        assertThat(updatedComment).isEqualTo(comment);
        assertThat(updatedComment.getText()).isEqualTo("Updated Text");
    }

    @Test
    void testUpdateWhenCommentDoesNotExistThenThrowEntityNotFoundException() {
        when(commentRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.update("1", "Updated Text"));
    }

    @Test
    void testDeleteByIdWhenCommentExistsThenDeleteComment() {
        doNothing().when(commentRepository).deleteById("1");

        commentService.deleteById("1");

        verify(commentRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteByBookIdWhenCommentsExistThenDeleteComments() {
        doNothing().when(commentRepository).deleteByBookId("1");

        commentService.deleteByBookId("1");

        verify(commentRepository, times(1)).deleteByBookId("1");
    }
}
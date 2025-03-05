package spring.otus.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.otus.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> findAllByBook(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select a from Comment as a where a.book.id=:bookid", Comment.class);
        query.setParameter("bookid", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return (comment);
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
    }


    @Override
    public void deleteAllByBookId(long bookId) {
        Query query = em.createQuery("delete from Comment as a where a.book.id=:bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }

}

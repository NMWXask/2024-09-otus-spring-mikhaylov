package spring.otus.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.otus.models.Author;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author as a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
            return Optional.of(em.find(Author.class, id));
    }

    @Override
    public Author save(Author book) {
        if (book.getId() == null) {
            em.persist(book);
            return (book);
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Author author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
        }
    }

}


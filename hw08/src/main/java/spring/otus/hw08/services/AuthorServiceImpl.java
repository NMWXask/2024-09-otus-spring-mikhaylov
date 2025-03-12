package spring.otus.hw08.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw08.models.Author;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.repositories.AuthorRepository;
import spring.otus.hw08.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
       authorRepository.deleteById(id);
        List<Book> books = bookRepository.findBooksByAuthorId(id);

        for (Book book : books) {
            bookRepository.deleteById(book.getId());
        }
    }
}

package spring.otus.hw08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import spring.otus.hw08.models.Author;
import spring.otus.hw08.models.Book;
import spring.otus.hw08.models.Comment;
import spring.otus.hw08.models.Genre;
import spring.otus.hw08.repositories.AuthorRepository;
import spring.otus.hw08.repositories.BookRepository;
import spring.otus.hw08.repositories.CommentRepository;
import spring.otus.hw08.repositories.GenreRepository;

@ChangeLog
public class InitMongoDB {

    private Author pushkin;

    private Author tolstoy;

    private Author lermontov;

    private Genre poema;

    private Genre roman;

    private Genre stih;

    private Book ruslan;

    private Book parus;

    private Book karenina;




    @ChangeSet(order = "002", id = "dropDB", author = "nwmxask", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "nwmxask", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        pushkin = repository.save(new Author(null, "Пушкин"));
        tolstoy = repository.save(new Author(null, "Толстой"));
        lermontov = repository.save(new Author(null, "Лермонтов"));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "nwmxask", runAlways = true)
    public void initGenres(GenreRepository repository) {
        poema = repository.save(new Genre(null, "Поэма"));
        roman = repository.save(new Genre(null, "Роман"));
        stih = repository.save(new Genre(null, "Стихотворение"));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "nwmxask", runAlways = true)
    public void initBooks(BookRepository repository) {
        ruslan = repository.save(new Book(null, "Руслан и Людмила", pushkin, poema));
        karenina = repository.save(new Book(null, "Анна Каренина", tolstoy, roman));
        parus = repository.save(new Book(null, "Парус", lermontov, stih));
    }

    @ChangeSet(order = "005", id = "initComments", author = "nwmxask", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment("Хорошая книга1", ruslan));
        repository.save(new Comment("Ничего но есть особенности1", ruslan));
        repository.save(new Comment("а мне не понравилось1", ruslan));

        repository.save(new Comment("Хорошая книга2", karenina));
        repository.save(new Comment("Ничего но есть особенности2", karenina));
        repository.save(new Comment("а мне не понравилось2", karenina));

        repository.save(new Comment("Хорошая книга3", parus));
        repository.save(new Comment("Ничего но есть особенности3", parus));
        repository.save(new Comment("а мне не понравилось3", parus));
    }

}

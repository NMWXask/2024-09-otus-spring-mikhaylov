package spring.otus.hw09.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.otus.hw09.dto.BookCreateDto;
import spring.otus.hw09.dto.BookDto;
import spring.otus.hw09.dto.BookUpdateDto;
import spring.otus.hw09.services.AuthorService;
import spring.otus.hw09.services.BookService;
import spring.otus.hw09.services.CommentService;
import spring.otus.hw09.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final CommentService commentService;

    @GetMapping("/")
    public String list(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        BookUpdateDto book = bookService.findById(id);
        model.addAttribute("referer", "/book/edit/" + id);
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("comments", commentService.findAllByBook(id));
        return "book/edit";
    }

    @GetMapping("/book/new")
    public String create(Model model) {
        BookCreateDto book = new BookCreateDto("", null, null);
        model.addAttribute("referer", "/book/new");
        model.addAttribute("book", book);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("comments", null);
        return "book/edit";
    }

    @PostMapping("/book/edit/{id}")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("book") BookUpdateDto book,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("referer", "/book/edit/" + id);
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("comments", commentService.findAllByBook(id));
            return "book/edit";
        }
        bookService.update(book);
        return "redirect:/book/edit/" + id;
    }

    @PostMapping("/book/new")
    public String create(@Valid @ModelAttribute("book") BookCreateDto book,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("referer", "/book/new");
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("comments", null);
            return "book/edit";
        }
        var bookCreated = bookService.create(book);
        return "redirect:/book/edit/" + bookCreated.id();
    }

    @PostMapping("/book/remove/{id}")
    public String remove(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
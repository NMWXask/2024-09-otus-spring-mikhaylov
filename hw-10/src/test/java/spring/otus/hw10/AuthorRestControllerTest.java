package spring.otus.hw10;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.otus.hw10.dto.AuthorDto;
import spring.otus.hw10.restController.AuthorRestController;
import spring.otus.hw10.services.AuthorService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void listShouldRenderAuthors() throws Exception {
        val authors = List.of(
                new AuthorDto(1L, "Pushkin"),
                new AuthorDto(1L, "Lermontov")
        );
        when(authorService.findAll()).thenReturn(authors);


        this.mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pushkin")))
                .andExpect(content().string(containsString("Lermontov")));
    }
}
package spring.otus.hw10;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.otus.hw10.dto.GenreDto;
import spring.otus.hw10.restController.GenreRestController;
import spring.otus.hw10.services.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GenreRestController.class)
class GenreRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    void listShouldRenderGenres() throws Exception {
        val genres = List.of(
                new GenreDto(1L, "Skazka"),
                new GenreDto(1L, "Detectiv")
        );
        when(genreService.findAll()).thenReturn(genres);


        this.mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Skazka")))
                .andExpect(content().string(containsString("Detectiv")));

    }

}
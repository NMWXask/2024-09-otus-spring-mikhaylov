package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for CsvQuestionDao")
public class CsvQuestionDaoTest {

    CsvQuestionDao dao;

    @Test
    @DisplayName("Должен вернуть список вопросов при корректном файле.")
    void shouldReturnListOfQuestionsCorrectly() {
        dao = new CsvQuestionDao("questions.csv");
        List<Question> questions = dao.findAll();

        assertFalse(questions.isEmpty());
        assertEquals(3, questions.size());
        assertEquals(Question.class, questions.get(0).getClass());
    }

    @Test
    @DisplayName("Должен выбросить исключение при некорректном файле.")
    void shouldThrowExceptionWhenResourceIsIncorrect() {

        dao = new CsvQuestionDao("incorrectResource.csv");

        assertThrows(IllegalArgumentException.class, () -> dao.findAll());
    }
}

package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import otus.spring.config.TestFileNameProvider;
import otus.spring.dao.CsvQuestionDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {

    @Test
    void findAll() {
        var fileNameProvider = mock(TestFileNameProvider.class);
        when(fileNameProvider.getTestFileName()).thenReturn("questionsTest.csv");
        var csvQuestionDao = new CsvQuestionDao(fileNameProvider);
        assertEquals(2, csvQuestionDao.findAll().size());
    }
}

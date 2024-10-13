package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.util.TestServiceImplUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Tests for TestServiceImpl")
public class TestServiceImplTest {

    IOService ioService;

    TestRunnerService testRunnerService;

    QuestionDao questionDao;

    TestServiceImpl testServiceImpl;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        testRunnerService = mock(TestRunnerServiceImpl.class);
        questionDao = mock(QuestionDao.class);
        testServiceImpl = new TestServiceImpl(testRunnerService, ioService);
    }

    @Test
    void shouldReturnListOfQuestions() {

        List<Question> questions = TestServiceImplUtil.getQuestions();

        when(questionDao.findAll()).thenReturn(questions);

        testServiceImpl.executeTest();

        assertEquals(questions, TestServiceImplUtil.getQuestions());
    }
}

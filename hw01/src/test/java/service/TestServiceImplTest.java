package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestServiceImpl;
import util.TestServiceImplUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Tests for TestServiceImpl")
public class TestServiceImplTest {

     IOService ioService;

     QuestionDao questionDao;

     TestServiceImpl testServiceImpl;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testServiceImpl = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void shouldReturnListOfQuestions() {

        List<Question> questions = TestServiceImplUtil.getQuestions();

        when(questionDao.findAll()).thenReturn(questions);

        testServiceImpl.executeTest();

        assertEquals(questions, TestServiceImplUtil.getQuestions());
    }
}

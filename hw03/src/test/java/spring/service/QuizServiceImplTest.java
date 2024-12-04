package spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import otus.spring.config.TestFileNameProvider;
import otus.spring.dao.CsvQuestionDao;
import otus.spring.domain.Student;
import otus.spring.service.IOService;
import otus.spring.service.QuizServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {

    @Test
    void executeTestFor() {

        var fileNameProvider = mock(TestFileNameProvider.class);
        when(fileNameProvider.getTestFileName()).thenReturn("questionsTest.csv");

        var ioService = Mockito.mock(IOService.class);
        var student = mock(Student.class);
        given(ioService.readIntForRange(anyInt(), anyInt(), anyString() ) )
                .willReturn(1);
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printFormattedColoredLine(anyString(),anyString());

        var questionDao = Mockito.spy(new CsvQuestionDao(fileNameProvider));
        var quizService = spy(new QuizServiceImpl(questionDao, ioService));

        assertEquals(quizService.executeTestFor(student).getRightAnswersCount() , 2);
    }
}
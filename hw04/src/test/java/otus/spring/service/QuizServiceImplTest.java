package otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import otus.spring.config.AppProps;
import otus.spring.domain.Student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@EnableConfigurationProperties(value = AppProps.class)
@SpringBootTest
class QuizServiceImplTest {

    @MockBean
    IOService ioService;

    @MockBean
    Student student;

    @Autowired
    QuizService quizService;

    @BeforeEach
    void setUp() {
        given(ioService.readIntForRange(anyInt(), anyInt(), anyString() ) )
                .willReturn(1);
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printFormattedColoredLine(anyString(),anyString());
    }

    @Test
    void RightAnswersCount() {
             var testResult = quizService.executeTestFor(student);
        assertEquals(testResult.getRightAnswersCount() , 2);
    }
}
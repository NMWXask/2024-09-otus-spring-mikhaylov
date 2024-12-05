package otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.spring.domain.Student;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private IOService ioService;

    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentServiceImpl(ioService);
    }

    @Test
    public void testDetermineCurrentStudentWhenCalledThenReturnsStudentWithExpectedNames() {
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        BDDMockito.given(ioService.readStringWithPrompt("StudentService.input.first.name")).willReturn(expectedFirstName);
        BDDMockito.given(ioService.readStringWithPrompt("StudentService.input.last.name")).willReturn(expectedLastName);

        Student result = studentService.determineCurrentStudent();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.firstName()).isEqualTo(expectedFirstName);
        Assertions.assertThat(result.lastName()).isEqualTo(expectedLastName);
    }
}
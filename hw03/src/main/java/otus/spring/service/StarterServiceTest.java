package otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.spring.exceptions.QuestionReadException;

import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_RED;

@Service
@RequiredArgsConstructor
public final class StarterServiceTest implements StarterService {

    private final QuizService quizService;

    private final IOService ioService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Override
    public  void startExam() {
        try {
            var student = studentService.determineCurrentStudent();
            var testResult = quizService.executeTestFor(student);
            resultService.showResult(testResult);

        } catch (QuestionReadException e) {
            ioService.printColoredLine("StarterService.ErrorReadingFile",
                    CONSOLE_TEXT_COLOR_RED);
        }
    }
}

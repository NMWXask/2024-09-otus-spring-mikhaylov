package otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.spring.dao.QuestionDao;
import otus.spring.domain.Question;
import otus.spring.domain.Student;
import otus.spring.domain.TestResult;
import java.util.concurrent.atomic.AtomicInteger;

import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_PURPLE;
import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_RESET;
import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_YELLOW;


@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuestionDao questionDao;

    private final IOService ioService;

    private int printAnswersForQuestionAndReturnRightAnswer(Question question, AtomicInteger answerNumber) {
        AtomicInteger rightAnswer = new AtomicInteger();
        question.answers().forEach(answer -> {
            answerNumber.set(answerNumber.get() + 1);
            ioService.printColoredLine("  " + answerNumber + ") " + answer.text(), CONSOLE_TEXT_COLOR_RESET);
            if (answer.isCorrect()) {
                rightAnswer.set(answerNumber.get());
            }
        });
        return rightAnswer.intValue();
    }


    private void printQuestion(Question question, AtomicInteger questionNumber) {
        ioService.printColoredLine("TestService.question" + questionNumber.toString(), CONSOLE_TEXT_COLOR_PURPLE);
        ioService.printColoredLine("  " + question.text(), CONSOLE_TEXT_COLOR_RESET);
        ioService.printColoredLine("TestService.answer.the.questions.possible", CONSOLE_TEXT_COLOR_YELLOW);
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedColoredLine("TestService.answer.the.questions", CONSOLE_TEXT_COLOR_YELLOW);

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        AtomicInteger questionNumber = new AtomicInteger();
        for (var question: questions) {
            questionNumber.set(questionNumber.get() + 1);
            printQuestion(question, questionNumber);
            AtomicInteger answerNumber = new AtomicInteger();
            int rightAnswer = printAnswersForQuestionAndReturnRightAnswer(question, answerNumber);
            int answer = ioService.readIntForRange(1, question.answers().size(), "invalid response");
            var isAnswerValid = rightAnswer == answer;
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}


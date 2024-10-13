package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        printTest(questions);
    }

    private void printTest(List<Question> questions) {
        int questionNumber = 1;
        for (Question question : questions) {
            printQuestion(question, questionNumber);
            printAnswerForQuestion(question);
            ioService.printLine("");
            questionNumber++;
        }
    }

    private void printAnswerForQuestion(Question question) {
        List<Answer> answers = question.answers();
        for (int i = 0; i < answers.size(); i++) {
            ioService.printFormattedLine("  %d) %s", i + 1, answers.get(i).text());
        }
    }

    private void printQuestion(Question question, int questionNumber) {
        ioService.printFormattedLine("Question %d: ", questionNumber);
        ioService.printFormattedLine("  %s", question.text());
        ioService.printFormattedLine("The following answer options are possible");
    }
}

package otus.spring.service;

import org.springframework.stereotype.Service;
import otus.spring.config.TestConfig;
import otus.spring.domain.TestResult;

import static otus.spring.utils.Const.CONSOLE_TEXT_COLOR_RESET;

@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;


    public ResultServiceImpl(TestConfig testConfig, IOService ioService) {
        this.testConfig = testConfig;
        this.ioService = ioService;
    }

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLine("ResultService.test.results");
        ioService.printFormattedColoredLine("ResultService.student",
                CONSOLE_TEXT_COLOR_RESET,
                testResult.getStudent().getFullName());
        ioService.printFormattedColoredLine("ResultService.answered.questions.count",
                CONSOLE_TEXT_COLOR_RESET,
                String.valueOf(testResult.getAnsweredQuestions().size()));
        ioService.printFormattedColoredLine("ResultService.right.answers.count",
                CONSOLE_TEXT_COLOR_RESET,
                String.valueOf(testResult.getRightAnswersCount()));

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine("ResultService.passed.test");
            return;
        }
        ioService.printLine("ResultService.fail.test");
    }
}

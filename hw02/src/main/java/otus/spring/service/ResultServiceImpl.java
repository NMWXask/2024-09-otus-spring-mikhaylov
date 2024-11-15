package otus.spring.service;

import org.springframework.stereotype.Service;
import otus.spring.config.TestConfig;
import otus.spring.domain.TestResult;

import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_RESET;

@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    public ResultServiceImpl(TestConfig testConfig, IOService ioService) {
        this.testConfig = testConfig;
        this.ioService = ioService;
    }

    @Override
    public void showResult(TestResult result) {
        ioService.printLine("");
        ioService.printLine("Test results: ");
        ioService.printFormattedColoredLine("Student: %s",
                CONSOLE_TEXT_COLOR_RESET,
                result.getStudent().getFullName());
        ioService.printFormattedColoredLine("Answered questions count: %d",
                CONSOLE_TEXT_COLOR_RESET,
                result.getAnsweredQuestions().size());
        ioService.printFormattedColoredLine("Right answers count: %d",
                CONSOLE_TEXT_COLOR_RESET,
                result.getRightAnswersCount());

        if (result.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLine("Congratulations! You passed test!");
            return;
        }
        ioService.printLine("Sorry. You fail test.");
    }
}

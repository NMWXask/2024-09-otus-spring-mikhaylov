package otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.spring.config.TestConfig;
import otus.spring.domain.TestResult;

import static otus.spring.enums.Const.CONSOLE_TEXT_COLOR_RESET;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

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

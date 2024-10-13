package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exception.QuestionReadException;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRunnerService testRunnerService;

    private final IOService ioService;

    @Override
    public void executeTest() {
        try {
            testRunnerService.run();
        } catch (QuestionReadException e) {
            ioService.printFormattedLine("Error reading test data", e);
        }
    }
}

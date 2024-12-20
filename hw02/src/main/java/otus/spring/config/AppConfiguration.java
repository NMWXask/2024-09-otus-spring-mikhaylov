package otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration implements TestConfig, TestFileNameProvider {

    @Value("${rightAnswersCountToPass}")
    private int rightAnswersCountToPass;

    @Value("${questions.csv}")
    private String testFileName;

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }
}

package otus.spring.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TestResult {
    private final Student student;

    private final List<Question> answeredQuestions;

    private int rightAnswersCount;

    public TestResult(Student student) {
        this.student = student;
        this.answeredQuestions = new ArrayList<>();
    }

    public void applyAnswer(Question question, boolean isRightAnswer) {
        answeredQuestions.add(question);
        if (isRightAnswer) {
            rightAnswersCount++;
        }
    }

    public String toString() {
        return "TestResult(student=" +
                this.getStudent() +
                ", answeredQuestions=" +
                this.getAnsweredQuestions() +
                ", rightAnswersCount=" +
                this.getRightAnswersCount() +
                ")";
    }
}

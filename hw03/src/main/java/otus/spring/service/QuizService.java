package otus.spring.service;


import otus.spring.domain.Student;
import otus.spring.domain.TestResult;

public interface QuizService {
    TestResult executeTestFor(Student student);
}
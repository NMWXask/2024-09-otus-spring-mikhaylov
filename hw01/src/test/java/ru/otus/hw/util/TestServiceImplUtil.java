package ru.otus.hw.util;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class TestServiceImplUtil {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("text1",List.of(new Answer("answer_text",true))));
        questions.add(new Question("text2",List.of(new Answer("answer_text",false))));
        questions.add(new Question("text3",List.of(new Answer("answer_text",true))));
        questions.add(new Question("text4",List.of(new Answer("answer_text",false))));
        questions.add(new Question("text5",List.of(new Answer("answer_text",true))));

        return questions;
    }
}

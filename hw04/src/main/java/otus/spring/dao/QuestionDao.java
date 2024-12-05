package otus.spring.dao;

import otus.spring.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}

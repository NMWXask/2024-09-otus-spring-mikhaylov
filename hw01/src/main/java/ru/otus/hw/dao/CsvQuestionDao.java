package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exception.QuestionReadException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvQuestionDao implements QuestionDao {

    private static final Logger LOGGER = Logger.getLogger(CsvQuestionDao.class.getName());

    @Override
    public List<Question> findAll() {

        String path = "hw01/src/main/resources/questions.csv";

        try (FileReader reader = new FileReader(path)) {

            List<QuestionDto> questions = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .build()
                    .parse();

            return questions.stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "CSV file not found: " + path, e);
            throw new QuestionReadException("CSV file not found: " + path, e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV file: " + path, e);
            throw new QuestionReadException("Error reading CSV file: " + path, e);
        }
    }
}

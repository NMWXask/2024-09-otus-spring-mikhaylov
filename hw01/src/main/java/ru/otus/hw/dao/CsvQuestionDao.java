package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exception.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private static final Logger LOGGER = Logger.getLogger(CsvQuestionDao.class.getName());

    private final Resource csvResource;

    @Override
    public List<Question> findAll() {

        try (InputStreamReader reader = new InputStreamReader(csvResource.getInputStream())) {
            List<QuestionDto> questions = parseCsv(reader);
            return convertToDomainObject(questions);
        } catch (IOException e) {
            handleException(e);
            return List.of();
        }
    }

    private List<QuestionDto> parseCsv(InputStreamReader reader) throws IOException {
        return new CsvToBeanBuilder<QuestionDto>(reader)
                .withSkipLines(1)
                .withType(QuestionDto.class)
                .build()
                .parse();
    }

    private List<Question> convertToDomainObject(List<QuestionDto> dtoList) {
        return dtoList.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }

    private void handleException(IOException e) {
        LOGGER.log(Level.SEVERE, "Error reading CSV file: " + csvResource.getFilename(), e);
        throw new QuestionReadException("Error reading CSV file: " + csvResource.getFilename(), e);
    }
}

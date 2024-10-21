package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exception.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private static final Logger LOGGER = Logger.getLogger(CsvQuestionDao.class.getName());

    private final String path;

    @Override
    public List<Question> findAll() {
        try (BufferedReader reader = readFileFromResourceAsStream(path)) {
            List<QuestionDto> questions = parseCsv(reader);
            return convertToDomainObject(questions);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV file: " + path, e);
            throw new QuestionReadException("Error reading CSV file: " + path, e);
        }
    }

    private BufferedReader readFileFromResourceAsStream(String filename) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("Could not find resource: " + filename);
        } else {
            InputStreamReader streamReader =
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return new BufferedReader(streamReader);
        }
    }

    private List<QuestionDto> parseCsv(BufferedReader reader) throws IOException {
        return new CsvToBeanBuilder<QuestionDto>(reader)
                .withSkipLines(1)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .build()
                .parse();
    }

    private List<Question> convertToDomainObject(List<QuestionDto> dtoList) {
        return dtoList.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }

}

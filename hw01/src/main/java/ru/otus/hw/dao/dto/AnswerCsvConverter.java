package ru.otus.hw.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import ru.otus.hw.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        var valueArray = value.split("%");
        return new Answer(valueArray[0], Boolean.parseBoolean(valueArray[1]));
    }
}

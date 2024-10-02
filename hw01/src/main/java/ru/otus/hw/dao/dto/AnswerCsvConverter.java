package ru.otus.hw.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return null;
    }
}

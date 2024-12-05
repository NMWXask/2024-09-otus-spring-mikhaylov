package otus.spring.service;

public interface IOService {
    void printLine(String s);

    void printColoredLine(String s, String colorLine);

    void printFormattedColoredLine(String s, String colorLine, String... args);

    String readStringWithPrompt(String prompt);

    int readIntForRange(int min, int max, String errorMessage);
}
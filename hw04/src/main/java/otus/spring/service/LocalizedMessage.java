package otus.spring.service;



public interface LocalizedMessage {
    String getLocalizedMessage(String stringForLocalize, String... args);
}

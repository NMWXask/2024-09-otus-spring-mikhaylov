package otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import otus.spring.config.LocaleProvider;

@Service
public class LocalizedMessageImpl implements LocalizedMessage {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public LocalizedMessageImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    public String getLocalizedMessage(String stringForLocalize, String... args) {
        return messageSource.getMessage(stringForLocalize, args, stringForLocalize , localeProvider.getLocale());
    }

}
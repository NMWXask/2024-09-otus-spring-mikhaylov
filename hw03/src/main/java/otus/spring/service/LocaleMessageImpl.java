package otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import otus.spring.config.LocaleProvider;

@Service
@RequiredArgsConstructor
public class LocaleMessageImpl implements LocaleMessage {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    @Override
    public String getLocaleMessage(String stringForLocalize, String... args) {
        return messageSource.getMessage(stringForLocalize, args, stringForLocalize, localeProvider.getLocale());
    }
}
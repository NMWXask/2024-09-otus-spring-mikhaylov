package otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunApplication implements CommandLineRunner {

    private final StarterService starter;

    @Override
    public void run(String... args) {
        starter.startExam();
    }
}

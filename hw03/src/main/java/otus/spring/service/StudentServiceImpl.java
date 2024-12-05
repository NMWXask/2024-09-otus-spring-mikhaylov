package otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Autowired
    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPrompt("StudentService.input.first.name");
        var lastName = ioService.readStringWithPrompt("StudentService.input.last.name");
        return new Student(firstName, lastName);
    }
}

package ru.otus.hw;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestService;

public class Application {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        TestService testService = context.getBean("testService", TestService.class);
        testService.executeTest();
    }
}

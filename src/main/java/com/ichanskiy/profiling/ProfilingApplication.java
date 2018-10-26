package com.ichanskiy.profiling;

import com.ichanskiy.profiling.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProfilingApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ProfilingApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        context.getBean(TestService.class).testMethod();
    }
}

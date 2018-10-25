package com.ichanskiy.profiling;

import com.ichanskiy.profiling.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class ProfilingApplication {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(ProfilingApplication.class, args);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void postConstruct() throws InterruptedException {
        while (true){
            Thread.sleep(100);
            testService.testMethod();
        }
    }
}

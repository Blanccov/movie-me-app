package com.example.moviewithme.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student marian = new Student(
                    "Marian",
                    "marian.jamal@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student aleks = new Student(
                    "Aleks",
                    "alex.jamal@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 5)
            );

            repository.saveAll(
                    List.of(marian, aleks)
            );
        };
    }
}

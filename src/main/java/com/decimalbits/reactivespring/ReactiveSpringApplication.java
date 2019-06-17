package com.decimalbits.reactivespring;

import com.decimalbits.reactivespring.model.Employee;
import com.decimalbits.reactivespring.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

import static com.decimalbits.reactivespring.CommonUtil.uuidSupplier;

@SpringBootApplication
public class ReactiveSpringApplication {

    @Bean
    CommandLineRunner employees(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.deleteAll()
                    .subscribe(null, null, () -> {
                        Stream.of(new Employee(uuidSupplier.get(), "Shivang Agarwal", 1000L),
                                new Employee(uuidSupplier.get(), "Prabhanshu", 1500L),
                                new Employee(uuidSupplier.get(), "Archit Agarwal", 2000L))
                              .forEach(employee -> {
                            employeeRepository.save(employee)
                                    .subscribe(System.out::println);
                        });
                    });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringApplication.class, args);
    }

}


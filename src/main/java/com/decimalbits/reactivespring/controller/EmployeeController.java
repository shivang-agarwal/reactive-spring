package com.decimalbits.reactivespring.controller;

import com.decimalbits.reactivespring.model.Employee;
import com.decimalbits.reactivespring.model.EmployeeEvent;
import com.decimalbits.reactivespring.repository.EmployeeRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     *
     * @return List of all the Employees with the help of Reactive Stream
     */
    @GetMapping("/all")
    public Flux<Employee> getAll(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getById(@PathVariable("id") final String id){
        return employeeRepository.findById(id);
    }

    @GetMapping(value = "{id}/events",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable final String id){
        return employeeRepository.findById(id)
                .flatMapMany( employee -> {
                    //Adding Artificial Delay to get the feel of real async programming.
                    Flux<Long> fluxDelay = Flux.interval(Duration.ofSeconds(2));
                    // creating strem of employee object
                    Flux<EmployeeEvent> employeeEventFlux = Flux.fromStream(Stream.generate( () -> new EmployeeEvent(employee,new Date())).limit(5));
                    //Zipping both the flux streams
                    return Flux.zip(employeeEventFlux,fluxDelay).map(Tuple2::getT1).log();
                });
    }

}

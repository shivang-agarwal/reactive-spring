package com.decimalbits.reactivespring.repository;

import com.decimalbits.reactivespring.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee,String> {



}

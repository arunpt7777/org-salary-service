package com.motta.salary_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motta.salary_service.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

}

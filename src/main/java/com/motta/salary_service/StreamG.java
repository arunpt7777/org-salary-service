package com.motta.salary_service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class StreamG {
    record Person(int id, String name, double salary, Department department) {}
    record Department(int id, String name) {}

    public static void main(String[] args) {
        List<Person> persons = List.of(
                new Person(1, "Alex", 100d, new Department(1, "HR")),
                new Person(2, "Brian", 200d, new Department(1, "HR")),
                new Person(3, "Charles", 900d, new Department(2, "Finance")),
                new Person(4, "David", 200d, new Department(2, "Finance")),
                new Person(5, "Edward", 200d, new Department(2, "Finance")),
                new Person(6, "Frank", 800d, new Department(3, "ADMIN")),
                new Person(7, "George", 900d, new Department(3, "ADMIN")));

        Map<Department, List<Person>> map = persons.stream().collect(groupingBy(Person::department));
        System.out.println(map);
        System.out.println("*********************************");
        Map<Department, List<Integer>> map = persons.stream()
                .collect(groupingBy(Person::department, mapping(Person::id, toList())));
        System.out.println(map);


    }
}

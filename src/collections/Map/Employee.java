package collections.Map;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Employee {

        String name;
        String dept;
        int salary;

        Employee(String name, String dept, int salary) {
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }

        public String getDept() {
            return dept;
        }

        public String toString() {
            return name;
        }

    public class Main {

        public static void main(String[] args) {

            List<Employee> employees = List.of(
                    new Employee("Alice", "HR", 50000),
                    new Employee("Bob", "HR", 60000),
                    new Employee("John", "IT", 70000),
                    new Employee("David", "IT", 80000),
                    new Employee("Emma", "FIN", 90000)
            );

            Map<String, List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getDept));

            Map<String,Long> map1 = employees.stream().collect(Collectors.groupingBy(Employee::getDept,Collectors.counting()));

            System.out.println(map);
        }
    }
}

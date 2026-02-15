package Classes;

import java.util.Arrays;

public class ExampleClass {
    public static void main(String[] args){
        Student[] students = new Student[3];

        students[0] = new Student("Sridhar",1);
        students[1] = new Student("Sajid",2);
        students[2] = new Student("Srikanth",3);

        for(Student s: students){
            System.out.println(s.name);
        }

        Arrays.sort(students,
                (obj1, obj2) -> obj2.getAge() - obj1.getAge());

        for(Student s:students){
            System.out.println(s.name);
        }

    }
}

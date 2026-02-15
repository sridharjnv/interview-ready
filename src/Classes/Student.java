package Classes;

import java.util.Comparator;

public class Student implements Comparable<Student>,Comparator<Student>  {
    String name;
    int age;

    Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Student s) {
        return this.age - s.age;
    }

    @Override
    public int compare(Student obj1, Student obj2) {
        return obj1.age - obj2.age;
    }
}

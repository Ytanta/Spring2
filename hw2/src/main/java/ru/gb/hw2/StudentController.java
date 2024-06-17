package ru.gb.hw2;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>();

    // GET /student/{id} - получить студента по ID
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = students.stream().filter(s -> s.getId().equals(id)).findFirst();
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  /student - получить всех студентов
    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return students;
    }

    // /student/search?name='studentName' - получить список студентов, чье имя содержит подстроку studentName
    @GetMapping("/student/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return students.stream()
                .filter(s -> s.getName().contains(name))
                .collect(Collectors.toList());
    }

    //Get /group/{groupName}/student - получить всех студентов группы
    @GetMapping("/group/{groupName}/student")
    public List<Student> getStudentsByGroupName(@PathVariable String groupName) {
        return students.stream()
                .filter(s -> s.getGroupName().equals(groupName))
                .collect(Collectors.toList());
    }

    // добавления студентов
    public Student addStudent(Student student) {
        students.add(student);
        return student;
    }

    //  при старте приложения добавим несколько тестовых студентов
    @PostConstruct
    public void initStudents() {
        addStudent(new Student(1l,"Vasy1", "12A"));
        addStudent(new Student(2l, "Vasy2", "12A"));
        addStudent(new Student(3l, "Vasy3", "13B"));
        addStudent(new Student(4l, "Vasy4", "13B"));
        addStudent(new Student(5l, "Vasy5", "15Y"));
    }
}
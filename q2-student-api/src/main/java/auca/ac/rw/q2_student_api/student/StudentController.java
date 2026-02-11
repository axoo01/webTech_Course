package auca.ac.rw.q2_student_api.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();

    public StudentController() {

        students.add(new Student(1L, "JD", "Muhirwa", "Muhirwa@gmail.com", "CS", 3.5));
        students.add(new Student(2L, "Eden", "Barikana", "eden@gmail.com", "EE", 3.8));
        students.add(new Student(3L, "Axcel", "Bro", "axcel@ex.com", "SE", 4.0));  
        students.add(new Student(4L, "Mike", "Ross", "mike@ex.com", "CS", 2.9));
        students.add(new Student(5L, "Harvey", "Specter", "Harvey@suits.com", "Lawyer", 3.2));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();  
    }

    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getMajor().equalsIgnoreCase(major)) {  
                result.add(s);
            }
        }
        return result;
    }

    @GetMapping("/filter")
    public List<Student> filterByGpa(@RequestParam Double gpa) {  
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getGpa() >= gpa) {
                result.add(s);
            }
        }
        return result;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student newStudent) {
        students.add(newStudent);
        return ResponseEntity.status(201).body(newStudent);  
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updated) {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getStudentId().equals(studentId)) {
          
                s.setFirstName(updated.getFirstName());
                s.setLastName(updated.getLastName());
                s.setEmail(updated.getEmail());
                s.setMajor(updated.getMajor());
                s.setGpa(updated.getGpa());
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();  
    }
}

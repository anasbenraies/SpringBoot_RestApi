package Springboot.Backend.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path= "/ressources")
public class StudentController {


    private final StudentService studentService ;

    @Autowired
        public StudentController(StudentService studentService) {
            this.studentService = studentService;
    }


    @GetMapping(path = "/students")
    public List<Student> getStudents() {
      return  studentService.getStudents();
    }


    @PostMapping(path = "/student")
    public ResponseEntity<String> saveStudnet(@RequestBody Student student) {
        return studentService.saveStudent(student);

    }

    @DeleteMapping(path = "student/{studentId}")
    public ResponseEntity<String> deleteStudent( @PathVariable("studentId") Long id){
        return studentService.deleteStudent(id);
    }

    @PutMapping(path = "/student/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable("studentId") Long id ,@RequestParam(required = false) String name ,@RequestParam(required = false) String email ){
        return studentService.updateStudent(id,name,email);
    }

}

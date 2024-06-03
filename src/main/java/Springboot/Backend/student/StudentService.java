package Springboot.Backend.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    public final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public ResponseEntity<String> saveStudent(Student student){
        //Check wether the student exists by email or not
        try {
            // Check whether the student exists by email or not
            Optional<Student> optionalStudent = studentRepository.findByEmail(student.getEmail());
            if (optionalStudent.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A student with the same email already exists");
            } else {
                studentRepository.save(student);
                return ResponseEntity.ok("Student saved successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    public ResponseEntity<String> deleteStudent(Long id) {
        try {
            if(studentRepository.existsById(id)){
                studentRepository.deleteById(id);
                return ResponseEntity.ok("Student deleted successfully");
            }else{
                return ResponseEntity.notFound().build();
            }
        }
        catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the student");
        }

    }

    @Transactional
    public ResponseEntity<String> updateStudent(Long id , String name , String email) {
        try {
            if(studentRepository.existsById(id)){
                Optional<Student> student = studentRepository.findById(id);
                Student TobeUpdatedStudent=  student.get();
                if (!TobeUpdatedStudent.getEmail().equals(email) && !TobeUpdatedStudent.getName().equals(name)){
                    TobeUpdatedStudent.setName(name);
                    TobeUpdatedStudent.setEmail(email);
                    //studentRepository.save(TobeUpdatedStudent);
                    return ResponseEntity.ok("Student updated successfully");

                }else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("the student has the same email or name ");
                }
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Student doesn't exist");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the student");
        }

    }
}



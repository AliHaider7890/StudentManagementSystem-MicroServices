package com.example.Course.Controller;

import com.example.Course.Dto.StudentVoucherDto;
import com.example.Course.Entity.Course;
import com.example.Course.Entity.Student;
import com.example.Course.Service.CourseService;
import com.example.Course.Service.StudentService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
  //  private final Course course;
    private final CourseService courseService;
   // Course course;

    public StudentController(StudentService service, CourseService courseService) {
        this.service = service;
     //   this.course = course;
        this.courseService = courseService;
    }

    // CREATE
    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.createStudent(student);
    }


    // READ BY ID
    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @GetMapping("/getByEmail/{email}")
    public Student getByEmail(@PathVariable String email) {
        return service.getStudentByEmail(email);
    }

    @PatchMapping("/students/{id}/{finalize}")
    public ResponseEntity<String> updateFinalizeStatus(
            @PathVariable Long id,
            @PathVariable Boolean finalize) {

      service.finalizedCourses(id , finalize);
//
//        student.setIsFinalized(request);
//
//        Student updated = re.save(student);

        return ResponseEntity.ok("Course has been Finalized ");
    }

//    @GetMapping("/voucher/{id}")
//    public String getVoucher(@PathVariable Long id) {
//        return service.getVoucher(id);
//    }

    @GetMapping("/voucher/{id}")
    public StudentVoucherDto getVoucher(
            @PathVariable Long id) {
        return service.getVoucher(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteStudent(id);
        return "Student deleted successfully";
    }

    @GetMapping("/getAllCourses")
    public List<Course> getAllCourses(){
        List<Course> allCourses = courseService.getAllCourses();
        return allCourses;
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<String> removeSingleCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        service.removeSingleCourse(studentId, courseId);
        return ResponseEntity.ok("Course removed successfully");
    }

    @GetMapping(value = "/{id}/voucher/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getVoucherPdf(@PathVariable Long id) {
        try {
            byte[] pdfBytes = service.getVoucherAsPdf(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "voucher_" + id + ".pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

//    @PutMapping("/assignCourse/{studentId}/{CourseId}")
//    public ResponseEntity<String> assignCourse(@PathVariable Integer studentId ,@PathVariable Integer CourseId){
//        service.assignCourse(studentId,CourseId);
//        return ResponseEntity.ok("Ok updated");
//    }

//
//    @GetMapping("/getInfo")
//    public Student getMyInfo() {
//        return studentService.getMyInfo();
//    }

    @PutMapping("/assignCourses/{studentId}/{CourseId}")
    public ResponseEntity<String> assignCourses(@PathVariable Long studentId ,@PathVariable Long CourseId){
        service.assignCourse(studentId,CourseId);
        return ResponseEntity.ok("Ok updated");
    }

}
package com.example.Course.Service;
import com.example.Course.Dto.CourseVoucherDto;
import com.example.Course.Dto.StudentVoucherDto;
import com.example.Course.Entity.Course;
import com.example.Course.Entity.Student;
import com.example.Course.Event.StudentCreateEvent;
import com.example.Course.Event.StudentCreationFailedEvent;
import com.example.Course.Exception.ResourceNotFoundException;
import com.example.Course.Repo.CourseRepo;
import com.example.Course.Repo.StudentRepository;

import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.Course.config.VoucherFlatRow;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.dialect.SybaseSqmToSqlAstConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository repo;
    private final CourseRepo courseRepo;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
 //   private final EnrollmentRepository enrollmentRepository;
 private final KafkaTemplate<String, StudentCreationFailedEvent> kafkaTemplate;


    public StudentService(StudentRepository repo , CourseRepo courseRepo, KafkaTemplate<String, StudentCreationFailedEvent> kafkaTemplate) {
        this.repo = repo;
        this.courseRepo = courseRepo;
      //  this.enrollmentRepository = enrollmentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Student createStudent(Student student) {
        return repo.save(student);
    }

//    @KafkaListener(topics = "student-created-topic")
//    public Student listen(StudentCreateEvent studentCreateEvent) {
//        log.info("Inside");
//        Student studentTemp = new Student();
//        studentTemp.setEmail(studentCreateEvent.getEmail());
//        repo.save(studentTemp);
//        System.out.println("Student from Kafka Test Saved");
//        return studentTemp;//    }s
//@KafkaListener(topics = "student-created-topic")
//public void listen(StudentCreateEvent event) {
//    try {
//        log.info("Received: {}", event.getEmail());
//        log.info("Received id is : {}", event.getId());
//        Student s = new Student();
//    //    s.setId(event.getId());
//        s.setId(event.getId());
//        s.setEmail(event.getEmail());
//        s.setName(event.getName());
//        s.setUserId(event.getId());
//        Student saved = repo.save(s);
//       // s.setId(event.getId());
//        log.info("Saved student id: {}", saved.getId());
//
//    } catch (Exception e)
//    {
//        log.error("Error saving student", e);
//        StudentCreationFailedEvent failedEvent =
//                new StudentCreationFailedEvent(
//                        event.getId(),
//                        event.getEmail()
//                );
//
//        kafkaTemplate.send(
//                "student-failed-topic",
//                failedEvent
//        );
//    }
//}

    public StudentVoucherDto getVoucher(Long id) {

        Student student = getStudentById(id);

        List<CourseVoucherDto> courseDtos = new ArrayList<>();

        double total = 0;

        for (Course course : student.getCourses()) {

            courseDtos.add(
                    new CourseVoucherDto(
                            course.getCourseName(),
                            course.getCoursePrice()
                    )
            );

            total += course.getCoursePrice();
        }

        return new StudentVoucherDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                courseDtos,
                total
        );
    }

    public byte[] getVoucherAsPdf(Long id) throws JRException {

        // 1. Tere existing method se DTO le
        StudentVoucherDto voucherDto = getVoucher(id);

        // 2. Flatten kar - har course ke liye ek row
        List<VoucherFlatRow> flatRows = new ArrayList<>();
        for (CourseVoucherDto course : voucherDto.getCourses()) {
            flatRows.add(new VoucherFlatRow(voucherDto, course));
        }

        // 3. Jasper data source bana
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(flatRows);

        // 4. Template load kar
        InputStream reportStream = getClass().getResourceAsStream("/voucher_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 5. Report fill kar
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        // 6. PDF byte array me convert kar
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

//    public String getVoucher(Long id) {
//
//        Student student = getStudentById(id);
//
//        StringBuilder voucher = new StringBuilder();
//
//        double total = 0;
//
//        voucher.append("Student Name : ")
//                .append(student.getName())
//                .append("\n\n");
//
//        for (Course course : student.getCourses()) {
//
//            voucher.append(course.getCourseName())
//                    .append(" : ")
//                    .append(course.getCoursePrice())
//                    .append("\n");
//
//            total += course.getCoursePrice();
//        }
//
//        voucher.append("\nTotal Amount : ")
//                .append(total);
//
//        return voucher.toString();
//    }

    @KafkaListener(topics = "student-created-topic")
    public void listen(StudentCreateEvent event) {

        try {

            log.info("Received: {}", event.getEmail());
            log.info("Received id is : {}", event.getId());
            Student s = new Student();
            s.setId(event.getId());
            s.setEmail(event.getEmail());
            s.setName(event.getName());
            s.setUserId(event.getId());
            Student saved = repo.save(s);

           // throw new RuntimeException("Student Save Failed");


log.info("Saved student id: {}", saved.getId());

        } catch (Exception e) {

            log.error("Error saving student", e);

            StudentCreationFailedEvent failedEvent =
                    new StudentCreationFailedEvent(
                            event.getId(),
                            event.getEmail()
                    );

            kafkaTemplate.send(
                    "student-failed-topic",
                    failedEvent
            );
        }
    }

    public Student getStudentById(Long id) {

        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student getStudentByEmail(String email) {

        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }



    public Student finalizedCourses(Long id, boolean finalize) {

        Student tempStudent = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        tempStudent.setIsFinalized(finalize);
        Boolean courseCheck = tempStudent.getIsFinalized();
        log.info("Yaha hun finalize pr");
        if(!courseCheck){
            scheduler.schedule(() -> {
                        try {
                            log.info("Click False - ");

                   //         log.info("Course has been finalized");

                            releaseCourses(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    5, TimeUnit.SECONDS);
        }
        else if (courseCheck) {
            log.info("Finaled Courses ");
        }
        else{
            log.info("Courses have been finalizing");
        }
//        scheduler.schedule(() -> {
//            try {
//                log.info("Inside Scheduler");
//
//                log.info("Course has been finalized");
//
//                releaseCourses(id);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        },
//                5, TimeUnit.SECONDS);

        return repo.save(tempStudent);
    }

    @Transactional
    public void releaseCourses(Long studentId) {

        Student student = repo.findByIdWithCourses(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        log.info("release course ke ander hun ");
        // 🔥 SAFETY CHECK (important)
//        if (!Boolean.FALSE.equals(student.getIsFinalized())) {
//            log.info("Returning from here");
//            return; // agar state change ho gayi ho toh release na ho
//        }

        List<Course> courses = new ArrayList<>(student.getCourses());
        // 👆 copy banayi taake concurrent issue na aaye

        for (Course course : courses) {
            course.setCourseQuantity(course.getCourseQuantity() + 1);
        }
        courseRepo.saveAll(courses);

        // 🔥 relation clear (join table clean)
        student.getCourses().clear();

        // optional: reset finalize
        student.setIsFinalized(false);

        repo.save(student);

        System.out.println("Released after 30 sec for student: " + studentId);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setAge(updatedStudent.getAge());
        return repo.save(student);
    }

    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public void assignCourse(Long StudentId , Long CourseId ){

        int updatedRows = courseRepo.decrementSeat(Long.valueOf(CourseId));
        if (updatedRows == 0) {
            throw new ResourceNotFoundException("No seats available for course " + CourseId);
        }
        Course tempCourse = courseRepo.findById(Long.valueOf(CourseId)).
                orElseThrow(() -> new ResourceNotFoundException("Course Not Found with this id " + CourseId));

        Student student = repo.findById(Long.valueOf(StudentId))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found" + StudentId));

        student.getCourses().add(tempCourse);

        int getCourseSeats = tempCourse.getCourseQuantity();
        repo.save(student);
    }

    @Transactional
    public void removeSingleCourse(Long studentId, Long courseId) {
        // 1. Student fetch karo with courses
        Student student = repo.findByIdWithCourses(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Course fetch karo
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 3. Check if student has this course
        if (!student.getCourses().contains(course)) {
            throw new RuntimeException("Student is not enrolled in this course");
        }

        // 4. Course quantity badhao (+1)
        course.setCourseQuantity(course.getCourseQuantity() + 1);
        courseRepo.save(course);

        // 5. Student se course remove karo
        student.getCourses().remove(course);

        // 6. Student save karo
        repo.save(student);

        log.info("Course {} removed from student {}", courseId, studentId);
    }

//    public Student getMyInfo() {
//
////        Authentication authentication = SecurityContextHolder
////                .getContext()
////                .getAuthentication();
////
////        String email = authentication.getName(); // email as username
//
//        Student student = repo
//                .findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        return student;
//    }

//    public void assignCourses(Long studentId, Long courseId) {
//
//        Student student = repo.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        Course course = courseRepo.findById(courseId)
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//
//        Enrollment enrollment = new Enrollment();
//        enrollment.setStudent(student);
//        enrollment.setCourse(course);
//
//
//        // hardcode ya dynamic
//   //     enrollment.setSemester("Fall2026");
//
//        enrollmentRepository.save(enrollment);
//    }
}

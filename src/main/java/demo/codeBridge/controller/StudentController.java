package demo.codeBridge.controller;

import demo.codeBridge.dto.request.StudentRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentRequestDto studentRequestDto) {
        StudentDto createStudent = studentService.createStudent(studentRequestDto);
        return ResponseEntity.ok(createStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentRequestDto studentRequestDto) {
        StudentDto updateStudent = studentService.updateStudent(id, studentRequestDto);
        return ResponseEntity.ok(updateStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable Long id) {
        StudentDto getStudent = studentService.getStudent(id);
        return ResponseEntity.ok(getStudent);
    }

    @PutMapping("/{studentId}/{trainingId}")
    public ResponseEntity<StudentDto> updateWithTraining(@PathVariable Long studentId, @PathVariable Long trainingId) {
        StudentDto updateStudentWithTrainings = studentService.updateStudentWithTraining(studentId, trainingId);
        return ResponseEntity.ok(updateStudentWithTrainings);
    }

    @GetMapping
    public ResponseEntity<Page<StudentDto>> list(Pageable pageable) {
        Page<StudentDto> list = studentService.getList(pageable);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

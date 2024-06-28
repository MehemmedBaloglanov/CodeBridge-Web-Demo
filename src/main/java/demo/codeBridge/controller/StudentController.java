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
public class        StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentRequestDto studentRequestDto) {
        return ResponseEntity.ok(studentService.createStudent(studentRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentRequestDto studentRequestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/{studentId}/{trainingId}")
    public ResponseEntity<StudentDto> updateWithTraining(@PathVariable Long studentId, @PathVariable Long trainingId) {
        return ResponseEntity.ok(studentService.updateStudentWithTraining(studentId, trainingId));
    }

    @GetMapping
    public ResponseEntity<Page<StudentDto>> list(Pageable pageable) {
        return ResponseEntity.ok(studentService.getList(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

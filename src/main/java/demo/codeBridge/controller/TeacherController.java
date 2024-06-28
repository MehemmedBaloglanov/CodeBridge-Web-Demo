package demo.codeBridge.controller;

import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherRequestDto teacherRequestDto) {
        return ResponseEntity.ok(teacherService.createTeacher(teacherRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @RequestBody TeacherRequestDto teacherRequestDto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherRequestDto));
    }

    @PutMapping("/{teacherId}/{trainingId}")
    public ResponseEntity<TeacherDto> updateWithTraining(@PathVariable Long teacherId, @PathVariable Long trainingId) {
        return ResponseEntity.ok(teacherService.updateTeacherWithTraining(teacherId, trainingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacher(id));
    }

    @GetMapping
    public ResponseEntity<Page<TeacherDto>> list(Pageable pageable) {
        return ResponseEntity.ok(teacherService.getList(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}

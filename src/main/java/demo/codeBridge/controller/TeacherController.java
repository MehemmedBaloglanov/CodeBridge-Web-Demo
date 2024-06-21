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
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherRequestDto teacherRequestDto){
        TeacherDto createTeacher=teacherService.createTeacher(teacherRequestDto);
        return ResponseEntity.ok(createTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @RequestBody TeacherRequestDto teacherRequestDto){
        TeacherDto updateTeacher=teacherService.updateTeacher(id,teacherRequestDto);
        return ResponseEntity.ok(updateTeacher);
    }

    @PutMapping("/{teacherId}/{trainingId}")
    public ResponseEntity<TeacherDto> update1(@PathVariable Long teacherId,@PathVariable Long trainingId){
        TeacherDto updateTeacherWithTrainings=teacherService.updateTeacherWithTraining(teacherId,trainingId);
        return ResponseEntity.ok(updateTeacherWithTrainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> get(@PathVariable Long id){
         TeacherDto getTeacher=teacherService.getTeacher(id);
         return ResponseEntity.ok(getTeacher);
    }

    @GetMapping
    public ResponseEntity<Page<TeacherDto>> list(Pageable pageable){
        Page<TeacherDto> list=teacherService.getList(pageable);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}

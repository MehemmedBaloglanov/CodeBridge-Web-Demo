package demo.codeBridge.controller;

import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public TeacherDto create(@RequestBody TeacherRequestDto teacherRequestDto){
        return teacherService.createTeacher(teacherRequestDto);
    }

    @PutMapping("/{id}")
    public TeacherDto update(@PathVariable Long id, @RequestBody TeacherRequestDto teacherRequestDto){
        return teacherService.updateTeacher(id,teacherRequestDto);
    }

    @PutMapping("/{teacherId}/{trainingId}")
    public TeacherDto update1(@PathVariable Long teacherId,@PathVariable Long trainingId){
        return teacherService.updateTeacherWithTraining(teacherId,trainingId);
    }
    @GetMapping("/{id}")
    public TeacherDto get(@PathVariable Long id){
        return teacherService.getTeacher(id);
    }

    @GetMapping
    public Page<TeacherDto> list(Pageable pageable){
        return teacherService.getList(pageable);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
}


}

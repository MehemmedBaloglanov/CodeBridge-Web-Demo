package demo.codeBridge.controller;


import demo.codeBridge.dto.request.StudentRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentDto create(@RequestBody StudentRequestDto studentRequestDto){
        return studentService.createStudent(studentRequestDto);
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable Long id, @RequestBody StudentRequestDto studentRequestDto){
        return studentService.updateStudent(id,studentRequestDto);
    }


    @GetMapping("/{id}")
    public StudentDto getStudentWithTraining(@PathVariable Long id){
        return studentService.getStudent(id);
    }

    @PutMapping("/{studentId}/{trainingId}")
    public StudentDto updateWithTraining(@PathVariable Long studentId, @PathVariable Long trainingId){
        return studentService.updateStudentWithTraining(studentId,trainingId);
    }



    @GetMapping
    public Page<StudentDto> list(Pageable pageable){
        return studentService.getList(pageable);
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }




}

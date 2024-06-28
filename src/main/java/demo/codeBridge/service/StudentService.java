package demo.codeBridge.service;

import demo.codeBridge.dto.request.StudentRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    StudentDto createStudent(StudentRequestDto studentRequestDto);
    StudentDto updateStudent(Long id, StudentRequestDto studentRequestDto);
    void deleteStudent(Long id);
    StudentDto updateStudentWithTraining(Long studentId, Long trainingId);
    Page<StudentDto> getList(Pageable pageable);
    StudentDto getStudent(Long id);
}

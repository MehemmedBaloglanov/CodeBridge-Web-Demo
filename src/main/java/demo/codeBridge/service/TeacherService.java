package demo.codeBridge.service;

import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {
    TeacherDto createTeacher(TeacherRequestDto teacherRequestDto);

    TeacherDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto);

    TeacherDto getTeacher(Long id);

    void deleteTeacher(Long id);

    TeacherDto updateTeacherWithTraining(Long teacherId,Long trainingId);

    Page<TeacherDto> getList(Pageable pageable);
}

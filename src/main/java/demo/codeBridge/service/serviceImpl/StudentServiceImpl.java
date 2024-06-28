package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.StudentRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.StudentEntity;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.StudentRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TrainingsRepository trainingsRepository;


    @Override
    public StudentDto createStudent(StudentRequestDto studentRequestDto) {
        StudentEntity student = StudentEntity.builder()
                .about(studentRequestDto.getAbout())
                .feedback(studentRequestDto.getFeedback())
                .build();
        StudentEntity savedEntity = studentRepository.save(student);
        return convertToDto(savedEntity);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
        student.setAbout(studentRequestDto.getAbout());
        student.setFeedback(studentRequestDto.getFeedback());

        StudentEntity updatedEntity = studentRepository.save(student);
        return convertToDto(updatedEntity);
    }

    @Override
    public StudentDto getStudent(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
        if(student.getTraining() != null) {
            return StudentDto.builder()
                    .id(student.getId())
                    .about(student.getAbout())
                    .feedback(student.getFeedback())
                    .training(TrainingsDto.builder()
                            .id(student.getTraining().getId())
                            .trainingName(student.getTraining().getTrainingName())
                            .build())
                    .build();
        }
        return StudentDto.builder()
                .id(student.getId())
                .about(student.getAbout())
                .feedback(student.getFeedback())
                .build();
    }

    @Override
    public StudentDto updateStudentWithTraining(Long studentId, Long trainingId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + studentId));
        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        student.setTraining(training);
        StudentEntity updatedEntity = studentRepository.save(student);
        return StudentDto.builder()
                .id(updatedEntity.getId())
                .about(updatedEntity.getAbout())
                .feedback(updatedEntity.getFeedback())
                .training(TrainingsDto.builder()
                        .id(updatedEntity.getTraining().getId())
                        .trainingName(updatedEntity.getTraining().getTrainingName())
                        .build())
                .build();
    }

    @Override
    public Page<StudentDto> getList(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(studentEntity -> {
                    StudentDto.StudentDtoBuilder builder = StudentDto.builder()
                            .id(studentEntity.getId())
                            .about(studentEntity.getAbout())
                            .feedback(studentEntity.getFeedback());

                    if (studentEntity.getTraining() != null &&
                            studentEntity.getTraining().getId() != null &&
                            studentEntity.getTraining().getTrainingName() != null) {
                        builder.training(TrainingsDto.builder()
                                .id(studentEntity.getTraining().getId())
                                .trainingName(studentEntity.getTraining().getTrainingName())
                                .build());
                    }

                    return builder.build();
                });
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }


    private StudentDto convertToDto(StudentEntity studentEntity) {
        return StudentDto.builder()
                .id(studentEntity.getId())
                .about(studentEntity.getAbout())
                .feedback(studentEntity.getFeedback())
                .build();
    }
}

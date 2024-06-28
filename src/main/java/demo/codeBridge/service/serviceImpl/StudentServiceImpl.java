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
        TrainingsEntity training = null;
        if (studentRequestDto.getTrainingId() != null) {
            training = trainingsRepository.findById(studentRequestDto.getTrainingId())
                    .orElseThrow(() -> new NotFoundException("Training not found with ID: " + studentRequestDto.getTrainingId()));
        }
        StudentEntity student = StudentEntity.builder()
                .about(studentRequestDto.getAbout())
                .feedback(studentRequestDto.getFeedback())
                .training(training)
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

        if (studentRequestDto.getTrainingId() != null) {
            TrainingsEntity training = trainingsRepository.findById(studentRequestDto.getTrainingId())
                    .orElseThrow(() -> new NotFoundException("Training not found with ID: " + studentRequestDto.getTrainingId()));
            student.setTraining(training);
        }

        StudentEntity updatedEntity = studentRepository.save(student);
        return convertToDto(updatedEntity);
    }

    @Override
    public StudentDto getStudent(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
        return convertToDto(student);
    }

    @Override
    public StudentDto updateStudentWithTraining(Long studentId, Long trainingId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + studentId));
        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        student.setTraining(training);
        StudentEntity updatedEntity = studentRepository.save(student);
        return convertToDto(updatedEntity);
    }

    @Override
    public Page<StudentDto> getList(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    private StudentDto convertToDto(StudentEntity studentEntity) {
        TrainingsDto trainingDto = null;
        if (studentEntity.getTraining() != null) {
            trainingDto = TrainingsDto.builder()
                    .id(studentEntity.getTraining().getId())
                    .trainingName(studentEntity.getTraining().getTrainingName())
                    .build();
        }
        return StudentDto.builder()
                .id(studentEntity.getId())
                .about(studentEntity.getAbout())
                .feedback(studentEntity.getFeedback())
                .training(trainingDto)
                .build();
    }
}

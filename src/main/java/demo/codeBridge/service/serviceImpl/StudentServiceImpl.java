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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final TrainingsRepository trainingsRepository;

    private final ModelMapper modelMapper;
    @Override
    public StudentDto createStudent(StudentRequestDto studentRequestDto) {
        StudentEntity student = StudentEntity.builder()
                .about(studentRequestDto.getAbout())
                .feedback(studentRequestDto.getFeedback())
                .build();
        StudentEntity save = studentRepository.save(student);
        return StudentDto.builder()
                .id(save.getId())
                .about(save.getAbout())
                .feedback(save.getFeedback())
                .build();
    }

    @Override
    public StudentDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id));
        student.setAbout(studentRequestDto.getAbout());
        student.setFeedback(studentRequestDto.getFeedback());

        StudentEntity save = studentRepository.save(student);
        return StudentDto.builder()
                .id(save.getId())
                .about(save.getAbout())
                .feedback(save.getFeedback())
                .build();
    }

    @Override
    public StudentDto getStudent(Long id1) {
        StudentEntity student = studentRepository.findById(id1)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id1));
        if (student.getTrainings() != null) {
            final TrainingsDto trainingsDto = modelMapper.map(student.getTrainings(), TrainingsDto.class);
            return StudentDto.builder()
                    .id(student.getId())
                    .about(student.getAbout())
                    .feedback(student.getFeedback())
                    .training(trainingsDto)
                    .build();

        }
        return StudentDto.builder()
                .id(student.getId())
                .about(student.getAbout())
                .feedback(student.getFeedback())
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
                    if (studentEntity.getTrainings() != null) {
                        builder.training(modelMapper.map(studentEntity.getTrainings(), TrainingsDto.class));
                    }
                    return builder.build();
                });
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudentWithTraining(Long studentId, Long trainingId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + studentId));
        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        student.setTrainings(training);
        StudentEntity save=studentRepository.save(student);
        final TrainingsDto trainingsDto = modelMapper.map(save.getTrainings(), TrainingsDto.class);
        return StudentDto.builder()
                .id(save.getId())
                .about(save.getAbout())
                .feedback(save.getFeedback())
                .training(trainingsDto)
                .build();
    }

}

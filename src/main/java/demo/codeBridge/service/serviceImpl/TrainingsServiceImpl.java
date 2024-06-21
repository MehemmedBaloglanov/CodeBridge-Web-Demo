package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.TeacherRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TrainingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingsServiceImpl implements TrainingsService {

    private final TrainingsRepository trainingsRepository;

    private final TeacherRepository teacherRepository;

    @Override
    public TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = TrainingsEntity.builder()
                .trainingName(trainingsRequestDto.getTrainingName())
                .build();
        TrainingsEntity save = trainingsRepository.save(trainings);
        return TrainingsDto.builder()
                .id(save.getId())
                .trainingName(save.getTrainingName())
                .build();
    }

    @Override
    public TrainingsDto updateTrainings(Long id, TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = trainingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + id));
        trainings.setTrainingName(trainingsRequestDto.getTrainingName());
        TrainingsEntity save = trainingsRepository.save(trainings);
        return TrainingsDto.builder()
                .id(save.getId())
                .trainingName(save.getTrainingName())
                .build();
    }

    @Override
    public TrainingsDto getTrainings(Long id1) {
        TrainingsEntity trainings = trainingsRepository.findById(id1)
                .orElseThrow(()->new NotFoundException("Training not found with ID: " + id1));
        return TrainingsDto.builder()
                .id(trainings.getId())
                .trainingName(trainings.getTrainingName())
                .build();
    }

    @Override
    public Page<TrainingsDto> getList(Pageable pageable) {
        return trainingsRepository.findAll(pageable)
                .map(trainingsEntity -> {
                    List<TeacherDto> teacherDtos = trainingsEntity.getTeacher().stream()
                            .map(teacher -> TeacherDto.builder()
                                    .id(teacher.getId())
                                    .about(teacher.getAbout())
                                    .contact(teacher.getContact())
                                    .picture(teacher.getPicture())
                                    .demoVideo(teacher.getDemoVideo())
                                    .build())
                            .collect(Collectors.toList());

                    List<StudentDto> studentDtos = trainingsEntity.getStudents().stream()
                            .map(student -> StudentDto.builder()
                                    .id(student.getId())
                                    .about(student.getAbout())
                                    .feedback(student.getFeedback())
                                    .build())
                            .collect(Collectors.toList());


                    return TrainingsDto.builder()
                            .id(trainingsEntity.getId())
                            .trainingName(trainingsEntity.getTrainingName())
                            .teacher(teacherDtos)
                            .students(studentDtos)
                            .build();
                });
    }

    @Override
    public void deleteTrainings(Long id) {
        teacherRepository.deleteById(id);
    }
}

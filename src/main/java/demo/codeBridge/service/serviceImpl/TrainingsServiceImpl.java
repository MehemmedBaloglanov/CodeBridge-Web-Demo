package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TrainingsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingsServiceImpl implements TrainingsService {

    private final TrainingsRepository trainingsRepository;
    private final ModelMapper modelMapper;

    @Override
    public TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = TrainingsEntity.builder()
                .trainingName(trainingsRequestDto.getTrainingName())
                .build();
        TrainingsEntity savedEntity = trainingsRepository.save(trainings);
        return TrainingsDto.builder()
                .id(savedEntity.getId())
                .trainingName(savedEntity.getTrainingName())
                .build();
    }

    @Override
    public TrainingsDto updateTrainings(Long id, TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = trainingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + id));
        trainings.setTrainingName(trainingsRequestDto.getTrainingName());
        TrainingsEntity updatedEntity = trainingsRepository.save(trainings);
        return TrainingsDto.builder()
                .id(updatedEntity.getId())
                .trainingName(updatedEntity.getTrainingName())
                .build();
    }

    @Override
    public TrainingsDto getTrainings(Long id) {
        TrainingsEntity trainings = trainingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + id));
        return convertToDto(trainings);
    }

    @Override
    public Page<TrainingsDto> getList(Pageable pageable) {
        return trainingsRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public void deleteTrainings(Long id) {
        if (!trainingsRepository.existsById(id)) {
            throw new NotFoundException("Training not found with ID: " + id);
        }
        trainingsRepository.deleteById(id);
    }

    private TrainingsDto convertToDto(TrainingsEntity trainingsEntity) {
        List<StudentDto> studentDtos = trainingsEntity.getStudents().stream()
                .map(studentEntity -> {
                    StudentDto studentDto = modelMapper.map(studentEntity, StudentDto.class);
                    studentDto.setTraining(null);
                    return studentDto;
                }).collect(Collectors.toList());

        List<TeacherDto> teacherDtos = trainingsEntity.getTeachers().stream()
                .map(teacherEntity -> {
                    TeacherDto teacherDto = modelMapper.map(teacherEntity, TeacherDto.class);
                    teacherDto.setTraining(null);
                    return teacherDto;
                }).collect(Collectors.toList());

        return TrainingsDto.builder()
                .id(trainingsEntity.getId())
                .trainingName(trainingsEntity.getTrainingName())
                .students(studentDtos)
                .teachers(teacherDtos)
                .build();
    }

}

package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.StudentRepository;
import demo.codeBridge.repository.TeacherRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TrainingsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainingsServiceImpl implements TrainingsService {

    private final TrainingsRepository trainingsRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = TrainingsEntity.builder()
                .trainingName(trainingsRequestDto.getTrainingName())
                .build();

        TrainingsEntity savedEntity = trainingsRepository.save(trainings);
        return convertToDto(savedEntity);
    }

    @Override
    public TrainingsDto updateTrainings(Long id, TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = trainingsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + id));
        trainings.setTrainingName(trainingsRequestDto.getTrainingName());

        TrainingsEntity updatedEntity = trainingsRepository.save(trainings);
        return convertToDto(updatedEntity);
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
                .map(trainingsEntity -> {
                    TrainingsDto.TrainingsDtoBuilder builder = TrainingsDto.builder()
                            .id(trainingsEntity.getId())
                            .trainingName(trainingsEntity.getTrainingName());

                    return builder.build();
                });
    }


    @Override
    public void deleteTrainings(Long id) {
        if (!trainingsRepository.existsById(id)) {
            throw new NotFoundException("Training not found with ID: " + id);
        }
        trainingsRepository.deleteById(id);
    }

    private TrainingsDto convertToDto(TrainingsEntity trainingsEntity) {
        return TrainingsDto.builder()
                .id(trainingsEntity.getId())
                .trainingName(trainingsEntity.getTrainingName())
                .build();
    }
}

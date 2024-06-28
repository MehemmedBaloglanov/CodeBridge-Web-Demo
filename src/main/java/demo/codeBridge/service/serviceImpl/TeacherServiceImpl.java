package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.TeacherEntity;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.TeacherRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TrainingsRepository trainingsRepository;
    private final ModelMapper modelMapper;

    @Override
    public TeacherDto createTeacher(TeacherRequestDto teacherRequestDto) {
        TrainingsEntity training = null;
        if (teacherRequestDto.getTrainingId() != null) {
            training = trainingsRepository.findById(teacherRequestDto.getTrainingId())
                    .orElseThrow(() -> new NotFoundException("Training not found with ID: " + teacherRequestDto.getTrainingId()));
        }
        TeacherEntity teacher = TeacherEntity.builder()
                .about(teacherRequestDto.getAbout())
                .contact(teacherRequestDto.getContact())
                .picture(teacherRequestDto.getPicture())
                .demoVideo(teacherRequestDto.getDemoVideo())
                .training(training)
                .build();
        TeacherEntity savedEntity = teacherRepository.save(teacher);
        return convertToDto(savedEntity);
    }

    @Override
    public TeacherDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + id));
        teacher.setAbout(teacherRequestDto.getAbout());
        teacher.setContact(teacherRequestDto.getContact());
        teacher.setPicture(teacherRequestDto.getPicture());
        teacher.setDemoVideo(teacherRequestDto.getDemoVideo());

        if (teacherRequestDto.getTrainingId() != null) {
            TrainingsEntity training = trainingsRepository.findById(teacherRequestDto.getTrainingId())
                    .orElseThrow(() -> new NotFoundException("Training not found with ID: " + teacherRequestDto.getTrainingId()));
            teacher.setTraining(training);
        }

        TeacherEntity updatedEntity = teacherRepository.save(teacher);
        return convertToDto(updatedEntity);
    }

    @Override
    public TeacherDto getTeacher(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + id));
        return convertToDto(teacher);
    }

    @Override
    public TeacherDto updateTeacherWithTraining(Long teacherId, Long trainingId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + teacherId));
        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        teacher.setTraining(training);
        TeacherEntity updatedEntity = teacherRepository.save(teacher);
        return convertToDto(updatedEntity);
    }

    @Override
    public Page<TeacherDto> getList(Pageable pageable) {
        return teacherRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new NotFoundException("Teacher not found with ID: " + id);
        }
        teacherRepository.deleteById(id);
    }

    private TeacherDto convertToDto(TeacherEntity teacherEntity) {
        TrainingsDto trainingDto = null;
        if (teacherEntity.getTraining() != null) {
            trainingDto = TrainingsDto.builder()
                    .id(teacherEntity.getTraining().getId())
                    .trainingName(teacherEntity.getTraining().getTrainingName())
                    .build();
        }
        return TeacherDto.builder()
                .id(teacherEntity.getId())
                .about(teacherEntity.getAbout())
                .contact(teacherEntity.getContact())
                .picture(teacherEntity.getPicture())
                .demoVideo(teacherEntity.getDemoVideo())
                .training(trainingDto)
                .build();
    }
}

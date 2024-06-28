package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.StudentDto;
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
        TeacherEntity teacher = TeacherEntity.builder()
                .about(teacherRequestDto.getAbout())
                .contact(teacherRequestDto.getContact())
                .picture(teacherRequestDto.getPicture())
                .demoVideo(teacherRequestDto.getDemoVideo())
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
        TeacherEntity updatedEntity = teacherRepository.save(teacher);
        return convertToDto(updatedEntity);
    }

    @Override
    public TeacherDto updateTeacherWithTraining(Long teacherId, Long trainingId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + teacherId));
        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        teacher.setTraining(training);
        TeacherEntity updatedEntity = teacherRepository.save(teacher);
        return TeacherDto.builder()
                .id(updatedEntity.getId())
                .about(updatedEntity.getAbout())
                .contact(updatedEntity.getContact())
                .picture(updatedEntity.getPicture())
                .demoVideo(updatedEntity.getDemoVideo())
                .training(TrainingsDto.builder()
                        .id(updatedEntity.getTraining().getId())
                        .trainingName(updatedEntity.getTraining().getTrainingName())
                        .build())
                .build();
    }

    @Override
    public TeacherDto getTeacher(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + id));
        if (teacher.getTraining() != null){
            return TeacherDto.builder()
                    .id(teacher.getId())
                    .about(teacher.getAbout())
                    .contact(teacher.getContact())
                    .picture(teacher.getPicture())
                    .demoVideo(teacher.getDemoVideo())
                    .training(TrainingsDto.builder()
                            .id(teacher.getTraining().getId())
                            .trainingName(teacher.getTraining().getTrainingName())
                            .build())
                    .build();
        }
        return TeacherDto.builder()
                .id(teacher.getId())
                .about(teacher.getAbout())
                .contact(teacher.getContact())
                .picture(teacher.getPicture())
                .demoVideo(teacher.getDemoVideo())
                .build();
    }

    @Override
    public Page<TeacherDto> getList(Pageable pageable) {
        return teacherRepository.findAll(pageable)
                .map(teacherEntity -> {
                    TeacherDto.TeacherDtoBuilder builder = TeacherDto.builder()
                            .id(teacherEntity.getId())
                            .about(teacherEntity.getAbout())
                            .contact(teacherEntity.getContact())
                            .picture(teacherEntity.getPicture())
                            .demoVideo(teacherEntity.getDemoVideo());

                    if (teacherEntity.getTraining() != null &&
                            teacherEntity.getTraining().getId() != null &&
                            teacherEntity.getTraining().getTrainingName() != null) {
                        builder.training(TrainingsDto.builder()
                                .id(teacherEntity.getTraining().getId())
                                .trainingName(teacherEntity.getTraining().getTrainingName())
                                .build());
                    }

                    return builder.build();
                });
    }


    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new NotFoundException("Teacher not found with ID: " + id);
        }
        teacherRepository.deleteById(id);
    }

    private TeacherDto convertToDto(TeacherEntity teacherEntity) {
        return TeacherDto.builder()
                .id(teacherEntity.getId())
                .about(teacherEntity.getAbout())
                .contact(teacherEntity.getContact())
                .picture(teacherEntity.getPicture())
                .demoVideo(teacherEntity.getDemoVideo())
                .build();
    }

}

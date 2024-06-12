package demo.codeBridge.service.serviceImpl;


import demo.codeBridge.dto.request.TeacherRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.StudentEntity;
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

@Service
@RequiredArgsConstructor
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
        TeacherEntity save = teacherRepository.save(teacher);
        return TeacherDto.builder()
                .id(save.getId())
                .about(save.getAbout())
                .contact(save.getContact())
                .picture(save.getPicture())
                .demoVideo(save.getDemoVideo())
                .build();
    }

    @Override
    public TeacherDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + id));
        teacher.setAbout(teacherRequestDto.getAbout());
        teacher.setContact(teacherRequestDto.getContact());
        teacher.setPicture(teacherRequestDto.getPicture());
        teacher.setDemoVideo(teacherRequestDto.getDemoVideo());

        TeacherEntity save = teacherRepository.save(teacher);
        return TeacherDto.builder()
                .id(save.getId())
                .about(save.getAbout())
                .contact(save.getContact())
                .picture(save.getPicture())
                .demoVideo(save.getDemoVideo())
                .build();
    }
    @Override
    public TeacherDto updateTeacherWithTraining(Long teacherId, Long trainingId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + teacherId));

        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        teacher.setTrainings(training);
        TeacherEntity save= teacherRepository.save(teacher);
        final TrainingsDto trainingsDto = modelMapper.map(save.getTrainings(), TrainingsDto.class);
        return TeacherDto.builder()
                .about(save.getAbout())
                .contact(save.getContact())
                .picture(save.getPicture())
                .demoVideo(save.getDemoVideo())
                .training(trainingsDto)
                .build();
    }

    @Override
    public TeacherDto getTeacher(Long id1) {
        TeacherEntity teacher = teacherRepository.findById(id1)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id1));
        if (teacher.getTrainings() != null) {
            final TrainingsDto trainingsDto = modelMapper.map(teacher.getTrainings(), TrainingsDto.class);
            return TeacherDto.builder()
                    .id(teacher.getId())
                    .about(teacher.getAbout())
                    .contact(teacher.getContact())
                    .picture(teacher.getPicture())
                    .demoVideo(teacher.getDemoVideo())
                    .training(trainingsDto)
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
                    if (teacherEntity.getTrainings() != null) {
                        builder.training(modelMapper.map(teacherEntity.getTrainings(), TrainingsDto.class));
                    }
                    return builder.build();
                });
    }




    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
}




}
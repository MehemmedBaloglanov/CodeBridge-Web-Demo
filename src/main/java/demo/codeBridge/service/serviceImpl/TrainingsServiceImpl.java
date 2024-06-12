package demo.codeBridge.service.serviceImpl;


import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.StudentDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TopicsDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.StudentEntity;
import demo.codeBridge.entity.TeacherEntity;
import demo.codeBridge.entity.TopicEntity;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.StudentRepository;
import demo.codeBridge.repository.TeacherRepository;
import demo.codeBridge.repository.TopicRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TrainingsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingsServiceImpl implements TrainingsService {

    private final TrainingsRepository trainingsRepository;

    private final TeacherRepository teacherRepository;

    private final TopicRepository topicRepository;
    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    @Override
    public TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto) {
        TrainingsEntity trainings = TrainingsEntity.builder()
                .trainingName(trainingsRequestDto.getTrainingName())
                .build();
        TrainingsEntity save = trainingsRepository.save(trainings);
        return TrainingsDto.builder()
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
                .trainingName(save.getTrainingName())
                .build();
    }

    @Override
    @Transactional
    public TrainingsDto updateWithTeacherId(Long teacherId, Long trainingId) {
        TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + teacherId));
        TrainingsEntity trainings = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        List<TeacherEntity> teachers = trainings.getTeacher();
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
        trainings.setTeacher(teachers);
        TrainingsEntity save = trainingsRepository.save(trainings);
        final TeacherDto teacherDto = modelMapper.map(save.getTeacher(), TeacherDto.class);
        return TrainingsDto.builder()
                .trainingName(save.getTrainingName())
                .teacher(List.of(teacherDto))
                .build();

    }

    @Override
    @Transactional
    public TrainingsDto updateByStudentId(Long studentId, Long trainingId) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + studentId));

        TrainingsEntity trainings = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));

        List<StudentEntity> students = trainings.getStudents();
        if (!students.contains(student)) {
            students.add(student);
        }
        trainings.setStudents(students);

        TrainingsEntity save = trainingsRepository.save(trainings);

        final StudentDto studentDto = modelMapper.map(save.getStudents(), StudentDto.class);

        return TrainingsDto.builder()
                .trainingName(save.getTrainingName())
                .students(List.of(studentDto))
                .build();
    }

    @Override
    public TrainingsDto updateWithTopicId(Long topicId, Long trainingId) {
        TopicEntity topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + topicId));
        TrainingsEntity trainings = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        List<TopicEntity> topics = trainings.getTopics();
        if (!topics.contains(topic)) {
            topics.add(topic);
        }
        trainings.setTopics(topics);
        TrainingsEntity save = trainingsRepository.save(trainings);
        final TopicsDto topicsDto = modelMapper.map(save.getTopics(), TopicsDto.class);
        return TrainingsDto.builder()
                .trainingName(save.getTrainingName())
                .topics(List.of(topicsDto))
                .build();
    }

    @Override
    public TrainingsDto getTrainings(Long id1) {
        TrainingsEntity trainings = trainingsRepository.findById(id1)
                .orElseThrow(()->new NotFoundException("Training not found with ID: " + id1));
        return TrainingsDto.builder()
                .trainingName(trainings.getTrainingName())
                .build();
    }

    @Override
    public TrainingsDto getTrainingsWithTeacher(Long id1) {
        TrainingsEntity trainings = trainingsRepository.findById(id1)
                .orElseThrow(()->new NotFoundException("Training not found with ID: " + id1));
        final TeacherDto teacherDto = modelMapper.map(trainings.getTeacher(), TeacherDto.class);
        return TrainingsDto.builder()
                .trainingName(trainings.getTrainingName())
                .teacher(List.of(teacherDto))
                .build();
    }

    @Override
    public TrainingsDto getTrainingsWithStudent(Long id1) {
        TrainingsEntity trainings = trainingsRepository.findById(id1)
                .orElseThrow(()->new NotFoundException("Training not found with ID: " + id1));
        final StudentDto studentDto = modelMapper.map(trainings.getStudents(), StudentDto.class);
        return TrainingsDto.builder()
                .trainingName(trainings.getTrainingName())
                .students(List.of(studentDto))
                .build();
    }

    @Override
    public TrainingsDto getTrainingsWithTopic(Long id1) {
        TrainingsEntity trainings = trainingsRepository.findById(id1)
                .orElseThrow(()->new NotFoundException("Training not found with ID: " + id1));
        final TopicsDto topicsDto = modelMapper.map(trainings.getTopics(), TopicsDto.class);
        return TrainingsDto.builder()
                .trainingName(trainings.getTrainingName())
                .topics(List.of(topicsDto))
                .build();
    }

    @Override
    public Page<TrainingsDto> getList(Pageable pageable) {
        return trainingsRepository.findAll(pageable)
                .map(trainingsEntity -> TrainingsDto.builder()
                        .trainingName(trainingsEntity.getTrainingName())
                        .teacher(List.of(modelMapper.map(trainingsEntity.getTeacher(),TeacherDto.class)))
                        .students(List.of(modelMapper.map(trainingsEntity.getStudents(),StudentDto.class)))
                        .topics(List.of(modelMapper.map(trainingsEntity.getTopics(),TopicsDto.class)))
                        .build());
    }


}

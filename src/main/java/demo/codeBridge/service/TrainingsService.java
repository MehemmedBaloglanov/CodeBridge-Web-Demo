package demo.codeBridge.service;

import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.TrainingsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingsService {
    TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto);

    TrainingsDto updateTrainings(Long id, TrainingsRequestDto trainingsRequestDto);

    TrainingsDto updateWithTeacherId(Long teacherId, Long trainingId);

    TrainingsDto updateByStudentId(Long studentId, Long trainingId);

    TrainingsDto updateWithTopicId(Long topicId, Long trainingId);

    TrainingsDto getTrainings(Long id1);

    TrainingsDto getTrainingsWithTeacher(Long id1);

    TrainingsDto getTrainingsWithStudent(Long id1);

    TrainingsDto getTrainingsWithTopic(Long id1);

    Page<TrainingsDto> getList(Pageable pageable);
}

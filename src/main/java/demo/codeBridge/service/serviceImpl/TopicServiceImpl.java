package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.TopicsRequestDto;
import demo.codeBridge.dto.response.TeacherDto;
import demo.codeBridge.dto.response.TopicsDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.entity.TopicEntity;
import demo.codeBridge.entity.TrainingsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.TopicRepository;
import demo.codeBridge.repository.TrainingsRepository;
import demo.codeBridge.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final TrainingsRepository trainingsRepository;

    private final ModelMapper modelMapper;
    @Override
    public TopicsDto createTopic(TopicsRequestDto topicsDto) {
        TopicEntity topic = TopicEntity.builder()
                .topic(topicsDto.getTopic())
                .build();
        TopicEntity save = topicRepository.save(topic);
        return TopicsDto.builder()
                .topic(save.getTopic())
                .build();
    }

    @Override
    public TopicsDto updateTopic(Long id, TopicsRequestDto topicsRequestDto) {
        TopicEntity topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Topic not found with ID: " + id));
        topic.setTopic(topicsRequestDto.getTopic());
        TopicEntity save = topicRepository.save(topic);
        return TopicsDto.builder()
                .topic(save.getTopic())
                .build();
    }

    @Override
    public TopicsDto updateTopicWithTraining(Long topicId, Long trainingId) {
        TopicEntity topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Teacher not found with ID: " + topicId));

        TrainingsEntity training = trainingsRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with ID: " + trainingId));
        topic.setTrainings(training);
        TopicEntity save= topicRepository.save(topic);
        final TrainingsDto trainingsDto = modelMapper.map(save.getTrainings(), TrainingsDto.class);
        return TopicsDto.builder()
                .topic(save.getTopic())
                .training(trainingsDto)
                .build();
    }

    @Override
    public TopicsDto getTopicWithTraining(Long id1) {
        TopicEntity topic = topicRepository.findById(id1)
                .orElseThrow(() -> new NotFoundException("Topic not found with ID: " + id1));
        final TrainingsDto trainingsDto = modelMapper.map(topic.getTrainings(), TrainingsDto.class);
        return TopicsDto.builder()
                .topic(topic.getTopic())
                .training(trainingsDto)
                .build();
    }

    @Override
    public Page<TopicsDto> getList(Pageable pageable) {
        return topicRepository.findAll(pageable)
                .map(topicEntity -> TopicsDto.builder()
                        .topic(topicEntity.getTopic())
                        .training(modelMapper.map(topicEntity.getTrainings(),TrainingsDto.class))
                        .build());
    }

    @Override
    public TopicsDto getTopic(Long id) {
        TopicEntity topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Topic not found with ID: " + id));
        return TopicsDto.builder()
                .topic(topic.getTopic())
                .build();
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
}




}

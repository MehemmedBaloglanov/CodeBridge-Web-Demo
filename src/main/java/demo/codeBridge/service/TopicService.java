package demo.codeBridge.service;


import demo.codeBridge.dto.request.TopicsRequestDto;
import demo.codeBridge.dto.response.TopicsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {
    TopicsDto createTopic(TopicsRequestDto topicsDto);

    TopicsDto updateTopic(Long id, TopicsRequestDto topicsRequestDto);

    void deleteTopic(Long id);

    TopicsDto getTopic(Long id);

    TopicsDto updateTopicWithTraining(Long topicId, Long trainingId);

    TopicsDto getTopicWithTraining(Long id1);

    Page<TopicsDto> getList(Pageable pageable);
}

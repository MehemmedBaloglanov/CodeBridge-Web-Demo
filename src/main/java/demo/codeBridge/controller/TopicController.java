package demo.codeBridge.controller;

import demo.codeBridge.dto.request.TopicsRequestDto;
import demo.codeBridge.dto.response.TopicsDto;
import demo.codeBridge.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public TopicsDto create(@RequestBody TopicsRequestDto topicsRequestDto){
        return topicService.createTopic(topicsRequestDto);
    }

    @PutMapping("/{id}")
    public TopicsDto update(@PathVariable Long id, @RequestBody TopicsRequestDto topicsRequestDto){
        return topicService.updateTopic(id,topicsRequestDto);
    }

    @PutMapping("/{topicId}/{trainingId}")
    public TopicsDto update1(@PathVariable Long topicId, @PathVariable Long trainingId){
        return topicService.updateTopicWithTraining(topicId,trainingId);
    }

    @GetMapping("/{id1}")
    public TopicsDto get1(@PathVariable Long id1) {
      return topicService.getTopicWithTraining(id1);
    }
    @GetMapping("/{id}")
    public TopicsDto get(@PathVariable Long id){
        return topicService.getTopic(id);
    }

    @GetMapping
    public Page<TopicsDto> list(Pageable pageable){
        return topicService.getList(pageable);
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){
        topicService.deleteTopic(id);
}


}

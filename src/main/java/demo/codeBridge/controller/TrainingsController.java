package demo.codeBridge.controller;
import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.service.TrainingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingsController {

    private final TrainingsService trainingsService;

    @PostMapping
    public TrainingsDto create(@RequestBody TrainingsRequestDto trainingsRequestDto){
        return trainingsService.createTrainings(trainingsRequestDto);
    }

    @PutMapping("/{id}")
    public TrainingsDto update(@PathVariable Long id, @RequestBody TrainingsRequestDto trainingsRequestDto){
        return trainingsService.updateTrainings(id,trainingsRequestDto);
    }

    @PutMapping("/{teacherId}/{trainingId}")
    public TrainingsDto updateWithTeacherID(@PathVariable Long teacherId,@PathVariable Long trainingId){
        return trainingsService.updateWithTeacherId(teacherId,trainingId);
    }

    @PutMapping("/{studentId}/{trainingId}")
    public TrainingsDto updateWithStudentID(@PathVariable Long studentId,@PathVariable Long trainingId){
        return trainingsService.updateByStudentId(studentId,trainingId);
    }

    @PutMapping("/{topicId}/{trainingId}")
    public TrainingsDto updateWithTopicId(@PathVariable Long topicId,@PathVariable Long trainingId){
        return trainingsService.updateWithTopicId(topicId,trainingId);
    }

    @GetMapping("/{id}")
    public TrainingsDto getTraining(@PathVariable Long id1){
        return trainingsService.getTrainings(id1);
    }

    @GetMapping("/{id1}")
    public TrainingsDto getTrainingWithTeacher(@PathVariable Long id1){
        return trainingsService.getTrainingsWithTeacher(id1);
    }

    @GetMapping("/{id2}")
    public TrainingsDto getTrainingWithStudent(@PathVariable Long id2){
        return trainingsService.getTrainingsWithStudent(id2);
    }

    @GetMapping("/{id3}")
    public TrainingsDto getTrainingWithTopic(@PathVariable Long id3){
        return trainingsService.getTrainingsWithTopic(id3);
    }

    @GetMapping
    public Page<TrainingsDto> list(Pageable pageable){
        return trainingsService.getList(pageable);
    }
}

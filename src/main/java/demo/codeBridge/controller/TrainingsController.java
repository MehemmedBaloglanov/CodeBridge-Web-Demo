package demo.codeBridge.controller;
import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.TrainingsDto;
import demo.codeBridge.service.TrainingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingsController {

    private final TrainingsService trainingsService;

    @PostMapping
    public ResponseEntity<TrainingsDto> create(@RequestBody TrainingsRequestDto trainingsRequestDto){
        TrainingsDto createTrainings= trainingsService.createTrainings(trainingsRequestDto);
        return ResponseEntity.ok(createTrainings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingsDto> update(@PathVariable Long id, @RequestBody TrainingsRequestDto trainingsRequestDto){
        TrainingsDto updateTrainings=trainingsService.updateTrainings(id,trainingsRequestDto);
        return ResponseEntity.ok(updateTrainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingsDto> getTraining(@PathVariable Long id){
        TrainingsDto getTrainings= trainingsService.getTrainings(id);
        return ResponseEntity.ok(getTrainings);
    }

    @GetMapping
    public ResponseEntity<Page<TrainingsDto>> list(Pageable pageable){
        Page<TrainingsDto> list=trainingsService.getList(pageable);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        trainingsService.deleteTrainings(id);
        return ResponseEntity.noContent().build();
    }
}

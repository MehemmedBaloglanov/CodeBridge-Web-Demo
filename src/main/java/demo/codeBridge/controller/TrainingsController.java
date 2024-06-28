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
    public ResponseEntity<TrainingsDto> create(@RequestBody TrainingsRequestDto trainingsRequestDto) {
        return ResponseEntity.ok(trainingsService.createTrainings(trainingsRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingsDto> update(@PathVariable Long id, @RequestBody TrainingsRequestDto trainingsRequestDto) {
        return ResponseEntity.ok(trainingsService.updateTrainings(id, trainingsRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingsDto> getTraining(@PathVariable Long id) {
        return ResponseEntity.ok(trainingsService.getTrainings(id));
    }

    @GetMapping
    public ResponseEntity<Page<TrainingsDto>> list(Pageable pageable) {
        return ResponseEntity.ok(trainingsService.getList(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainingsService.deleteTrainings(id);
        return ResponseEntity.noContent().build();
    }
}

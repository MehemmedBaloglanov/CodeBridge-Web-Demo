package demo.codeBridge.service;

import demo.codeBridge.dto.request.TrainingsRequestDto;
import demo.codeBridge.dto.response.TrainingsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingsService {
    TrainingsDto createTrainings(TrainingsRequestDto trainingsRequestDto);
    TrainingsDto updateTrainings(Long id, TrainingsRequestDto trainingsRequestDto);
    TrainingsDto getTrainings(Long id);
    Page<TrainingsDto> getList(Pageable pageable);
    void deleteTrainings(Long id);
}

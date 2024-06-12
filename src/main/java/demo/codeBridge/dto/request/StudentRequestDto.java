package demo.codeBridge.dto.request;

import demo.codeBridge.entity.TrainingsEntity;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class StudentRequestDto {
    private String about;
    private String feedback;
    private TrainingsRequestDto training;
}

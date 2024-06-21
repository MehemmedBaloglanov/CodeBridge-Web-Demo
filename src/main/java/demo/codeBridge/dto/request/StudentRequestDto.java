package demo.codeBridge.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequestDto {
    private String about;
    private String feedback;
    private TrainingsRequestDto training;
}

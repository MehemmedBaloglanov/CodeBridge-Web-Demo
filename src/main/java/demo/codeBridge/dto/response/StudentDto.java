package demo.codeBridge.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StudentDto {
    private Long id;
    private String about;
    private String feedback;
    private TrainingsDto training;
}

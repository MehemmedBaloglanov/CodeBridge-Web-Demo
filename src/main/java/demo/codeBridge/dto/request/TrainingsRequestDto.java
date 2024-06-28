package demo.codeBridge.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class TrainingsRequestDto {
    private String trainingName;

    @JsonIgnore
    private List<TeacherRequestDto> teachers;
    @JsonIgnore
    private List<StudentRequestDto> students;
}

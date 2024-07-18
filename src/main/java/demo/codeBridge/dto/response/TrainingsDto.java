package demo.codeBridge.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingsDto {

    private Long id;

    private String trainingName;

    @JsonIgnore
    private List<StudentDto> students;

    @JsonIgnore
    private List<TeacherDto> teachers;
}

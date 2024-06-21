package demo.codeBridge.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class TrainingsRequestDto {

    private String trainingName;

    private List<TeacherRequestDto> teacher;

    private List<StudentRequestDto> students;

}

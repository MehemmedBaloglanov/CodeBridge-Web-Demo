package demo.codeBridge.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class TrainingsDto {
    private Long id;

    private String trainingName;


    private List<TeacherDto> teacher;

    private List<StudentDto> students;

    private List<TopicsDto> topics;
}

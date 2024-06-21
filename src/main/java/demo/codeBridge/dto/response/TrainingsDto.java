package demo.codeBridge.dto.response;


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

    private List<TeacherDto> teacher;

    private List<StudentDto> students;


}

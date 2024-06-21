package demo.codeBridge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private Long id;
    private String about;
    private String contact;
    private String picture;
    private String demoVideo;
    private TrainingsDto training;
}

package demo.codeBridge.dto.request;

import demo.codeBridge.dto.response.TrainingsDto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TeacherRequestDto {

    private String about;
    private String contact;
    private String picture;
    private String demoVideo;
    private TrainingsRequestDto training;
}

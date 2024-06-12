package demo.codeBridge.dto.response;


import demo.codeBridge.entity.TrainingsEntity;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class TeacherDto {

    private Long id;
    private String about;
    private String contact;
    private String picture;
    private String demoVideo;
    private TrainingsDto training;
}

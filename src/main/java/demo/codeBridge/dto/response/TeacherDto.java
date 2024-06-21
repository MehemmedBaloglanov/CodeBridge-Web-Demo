package demo.codeBridge.dto.response;


import demo.codeBridge.entity.TrainingsEntity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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

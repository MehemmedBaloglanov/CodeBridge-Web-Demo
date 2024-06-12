package demo.codeBridge.dto.response;

import demo.codeBridge.entity.TrainingsEntity;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicsDto {

    private Long id;

    private String topic;

    private TrainingsDto training;

}

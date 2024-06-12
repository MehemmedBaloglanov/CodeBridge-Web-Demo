package demo.codeBridge.dto.request;


import demo.codeBridge.dto.response.TrainingsDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicsRequestDto {

    private String topic;

    private TrainingsRequestDto training;

}

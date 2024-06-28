package demo.codeBridge.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsRequestDto {

    private String title;
    private String author;
    private String content;
    private String category;
    private LocalDateTime publicationDate;
    private String imageUrl;
}

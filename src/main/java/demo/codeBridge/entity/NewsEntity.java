package demo.codeBridge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String content;
    private String category;
    private LocalDateTime publicationDate;
    private LocalDateTime lastUpdatedDate;
    private String imageUrl;

    @Builder.Default
    private Integer viewCount=0;
}

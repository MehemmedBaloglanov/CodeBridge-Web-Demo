package demo.codeBridge.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topics")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingsEntity trainings;
}

package demo.codeBridge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String about;
    private String contact;
    private String picture;
    private String demoVideo;
    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingsEntity trainings;
}

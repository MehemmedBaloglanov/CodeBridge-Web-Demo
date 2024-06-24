package demo.codeBridge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "teachers")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String about;
    private String contact;
    private String picture;
    private String demoVideo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingsEntity trainings;
}

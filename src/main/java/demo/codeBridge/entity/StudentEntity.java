package demo.codeBridge.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "students")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String about;
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private TrainingsEntity trainings;

}

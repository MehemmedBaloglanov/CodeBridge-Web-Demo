package demo.codeBridge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "trainings")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainingName;

    @OneToMany(mappedBy = "trainings")
    private List<TeacherEntity> teacher;

    @OneToMany(mappedBy = "trainings")
    private List<StudentEntity> students;

    @OneToMany(mappedBy = "trainings")
    private List<TopicEntity> topics;
}

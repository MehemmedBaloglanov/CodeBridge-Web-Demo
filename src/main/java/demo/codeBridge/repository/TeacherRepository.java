package demo.codeBridge.repository;

import demo.codeBridge.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity,Long> {
}
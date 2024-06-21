package demo.codeBridge.repository;

import demo.codeBridge.entity.TrainingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingsRepository extends JpaRepository<TrainingsEntity,Long> {
}

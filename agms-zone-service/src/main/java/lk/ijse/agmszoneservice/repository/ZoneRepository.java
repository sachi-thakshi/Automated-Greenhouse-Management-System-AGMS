package lk.ijse.agmszoneservice.repository;

import lk.ijse.agmszoneservice.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
}

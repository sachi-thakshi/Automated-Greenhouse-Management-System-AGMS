package lk.ijse.agmscropservice.repository;

import lk.ijse.agmscropservice.entity.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CropRepository extends MongoRepository<Crop, String> {
    List<Crop> findByZoneId(Long zoneId);
}
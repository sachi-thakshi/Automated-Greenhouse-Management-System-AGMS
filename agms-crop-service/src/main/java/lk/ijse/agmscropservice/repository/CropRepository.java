package lk.ijse.agmscropservice.repository;

import lk.ijse.agmscropservice.entity.Crop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CropRepository extends MongoRepository<Crop, String> {

}

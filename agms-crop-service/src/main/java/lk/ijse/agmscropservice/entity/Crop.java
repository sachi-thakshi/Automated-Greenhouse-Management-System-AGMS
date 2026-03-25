package lk.ijse.agmscropservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crops")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Crop {
    @Id
    private String id;
    private String cropName;
    private String scientificName;
    private String category;
    private String growthStatus;
    private String cropSeason;
    private Long zoneId;
}
package lk.ijse.agmscropservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "crops")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Crop {
    @Id
    private String id;
    private String batchId;
    private String cropName;
    private String category;

    // State Machine: SEEDLING, VEGETATIVE, HARVESTED
    private String status;

    private Long zoneId;
    private LocalDate plantedDate;
}
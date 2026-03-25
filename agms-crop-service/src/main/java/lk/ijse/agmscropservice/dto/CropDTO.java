package lk.ijse.agmscropservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private String id;
    private String cropName;
    private String scientificName;
    private String category;
    private String growthStatus;
    private String cropSeason;
    private Long zoneId;
}
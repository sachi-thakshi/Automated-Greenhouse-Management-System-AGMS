package lk.ijse.agmszoneservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTO {
    private Long id;
    private String zoneName;
    private String description;
    private Double areaSize;
}

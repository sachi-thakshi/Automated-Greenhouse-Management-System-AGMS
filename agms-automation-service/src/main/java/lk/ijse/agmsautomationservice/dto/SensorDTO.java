package lk.ijse.agmsautomationservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SensorDTO {
    private Double temperature;
    private Double humidity;
    private Long zoneId;
    private LocalDateTime timestamp;
}
package lk.ijse.agmszoneservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;

    private String description;

    private Double areaSize;

    private Double minTemp;
    private Double maxTemp;

    private String deviceId;
}
package lk.ijse.agmszoneservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("zoneName")
    private String name;

    private String description;

    private Double totalArea;

    @JsonProperty("areaSize")
    public Double getTotalArea() {
        return totalArea;
    }

    @JsonProperty("areaSize")
    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }
}
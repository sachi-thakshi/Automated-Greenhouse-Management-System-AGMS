package lk.ijse.agmssensorservice.controller;

import lk.ijse.agmssensorservice.dto.SensorDTO;
import lk.ijse.agmssensorservice.service.SensorTelemetryTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorTelemetryTask telemetryTask;

    @GetMapping("/latest")
    public ResponseEntity<SensorDTO> getLatest() {
        SensorDTO latest = telemetryTask.getLatestReading();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.noContent().build();
    }
}
package lk.ijse.agmsautomationservice.controller;

import lk.ijse.agmsautomationservice.dto.SensorDTO;
import lk.ijse.agmsautomationservice.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/process")
    public ResponseEntity<Void> receiveTelemetry(@RequestBody SensorDTO data) {
        automationService.processEnvironmentalData(data);
        return ResponseEntity.ok().build();
    }
}
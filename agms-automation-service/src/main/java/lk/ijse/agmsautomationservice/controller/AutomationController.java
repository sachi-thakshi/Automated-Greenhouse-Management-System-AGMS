package lk.ijse.agmsautomationservice.controller;

import lk.ijse.agmsautomationservice.dto.TelemetryDTO;
import lk.ijse.agmsautomationservice.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/process")
    public void receiveTelemetry(@RequestBody TelemetryDTO telemetryDTO) {
        automationService.processTelemetry(telemetryDTO);
    }
}
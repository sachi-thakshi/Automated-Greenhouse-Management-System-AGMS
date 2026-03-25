package lk.ijse.agmsautomationservice.service;

import lk.ijse.agmsautomationservice.dto.TelemetryDTO;

public interface AutomationService {
    void processTelemetry(TelemetryDTO telemetryDTO);
}

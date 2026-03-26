package lk.ijse.agmsautomationservice.service;

import lk.ijse.agmsautomationservice.dto.SensorDTO;

public interface AutomationService {
    void processEnvironmentalData(SensorDTO data);

    void triggerAction(Long zoneId, String device, String state, String reason);
}
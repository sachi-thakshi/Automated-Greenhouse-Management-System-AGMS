package lk.ijse.agmsautomationservice.service.impl;

import lk.ijse.agmsautomationservice.dto.SensorDTO;
import lk.ijse.agmsautomationservice.service.AutomationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutomationServiceImpl implements AutomationService {

    @Override
    public void processEnvironmentalData(SensorDTO data) {
        log.info("Received Telemetry for Zone {}: Temp={}C, Humidity={}%",
                data.getZoneId(), data.getTemperature(), data.getHumidity());

        if (data.getTemperature() > 30.0) {
            triggerAction(data.getZoneId(), "COOLING_FAN", "START", "Temperature high (" + data.getTemperature() + "C)");
        } else if (data.getTemperature() < 18.0) {
            triggerAction(data.getZoneId(), "HEATER", "START", "Temperature low (" + data.getTemperature() + "C)");
        }

        if (data.getHumidity() < 40.0) {
            triggerAction(data.getZoneId(), "IRRIGATION_PUMP", "START", "Humidity critical (" + data.getHumidity() + "%)");
        }
    }

    @Override
    public void triggerAction(Long zoneId, String device, String state, String reason) {
        log.warn(">>> [HARDWARE COMMAND] Zone: {} | Device: {} | Action: {} | Reason: {}",
                zoneId, device, state, reason);
    }
}
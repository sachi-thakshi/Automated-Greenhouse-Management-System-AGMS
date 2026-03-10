package lk.ijse.agmsautomationservice.controller;

import lk.ijse.agmsautomationservice.dto.Telemetry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    private final List<String> logs = new ArrayList<>();

    @PostMapping("/process")
    public String process(@RequestBody Telemetry data){

        if(data.getTemperature() > 32){
            logs.add("TURN_FAN_ON");
        }

        if(data.getTemperature() < 18){
            logs.add("TURN_HEATER_ON");
        }

        return "Processed";
    }

    @GetMapping("/logs")
    public List<String> getLogs(){
        return logs;
    }

}

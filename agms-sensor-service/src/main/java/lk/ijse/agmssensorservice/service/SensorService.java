package lk.ijse.agmssensorservice.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SensorService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void fetchTelemetry(){

        String url = "http://104.211.95.241:8080/api/devices";

        try{
            String response = restTemplate.getForObject(url,String.class);
            System.out.println("Sensor Data: "+response);
        }catch(Exception e){
            System.out.println("Error fetching telemetry");
        }

    }

}

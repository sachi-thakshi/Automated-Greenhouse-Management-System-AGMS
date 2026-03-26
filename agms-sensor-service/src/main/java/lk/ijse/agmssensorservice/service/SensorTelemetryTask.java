package lk.ijse.agmssensorservice.service;

import lk.ijse.agmssensorservice.dto.SensorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SensorTelemetryTask {

    private final RestTemplate restTemplate;
    private final RestTemplate loadBalancedRestTemplate;

    private String cachedToken;
    private SensorDTO lastReading;

    @Value("${iot.external.auth-url:http://104.211.95.241/api/v1/auth/authenticate}")
    private String authUrl;

    @Value("${iot.external.data-url:http://104.211.95.241/api/iot/data}")
    private String dataUrl;

    @Value("${iot.external.username:username}")
    private String username;

    @Value("${iot.external.password:123456}")
    private String password;

    private final String AUTOMATION_SERVICE_URL = "http://automation-service/api/v1/automation/process";

    public SensorTelemetryTask(
            RestTemplate restTemplate,
            @Qualifier("loadBalancedRestTemplate") RestTemplate loadBalancedRestTemplate) {
        this.restTemplate = restTemplate;
        this.loadBalancedRestTemplate = loadBalancedRestTemplate;
    }

    private void refreshToken() {
        try {
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("email", username);
            loginRequest.put("password", password);

            ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, loginRequest, Map.class);
            if (response.getBody() != null) {
                this.cachedToken = (String) response.getBody().get("token");
                log.info("Successfully authenticated with External IoT. Token cached.");
            }
        } catch (Exception e) {
            log.error("Authentication Failed: {}. Using mock fallback if fetch fails.", e.getMessage());
        }
    }

    @Scheduled(fixedRate = 10000)
    public void fetchAndPushTelemetry() {
        if (cachedToken == null) {
            refreshToken();
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(cachedToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<SensorDTO> response = restTemplate.exchange(
                    dataUrl, HttpMethod.GET, entity, SensorDTO.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                processAndPush(response.getBody());
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            log.warn("Token expired. Clearing cache to re-authenticate next cycle.");
            this.cachedToken = null;
        } catch (Exception e) {
            log.error("Connection Error: {}. Falling back to MOCK data.", e.getMessage());
            SensorDTO mockData = new SensorDTO(28.5, 72.0, 1L, LocalDateTime.now());
            processAndPush(mockData);
        }
    }

    private void processAndPush(SensorDTO data) {
        data.setTimestamp(LocalDateTime.now());
        this.lastReading = data;
        log.info("Pushing Telemetry: {}C, {}%", data.getTemperature(), data.getHumidity());

        loadBalancedRestTemplate.postForEntity(AUTOMATION_SERVICE_URL, data, Void.class);
    }

    public SensorDTO getLatestReading() {
        return lastReading;
    }
}
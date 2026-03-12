package lk.ijse.agmszoneservice.service;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import lk.ijse.agmszoneservice.entity.Zone;
import lk.ijse.agmszoneservice.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    private final String IOT_API_BASE_URL = "http://104.211.95.241:8080/api";

    private String getExternalIoTToken() {
        try {
            Map<String, String> loginRequest = new HashMap<>();
            loginRequest.put("username", "username");
            loginRequest.put("password", "123456");

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    IOT_API_BASE_URL + "/auth/login",
                    loginRequest,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("accessToken");
            }
            throw new RuntimeException("External Auth Failed");
        } catch (Exception e) {
            throw new RuntimeException("Connection to IoT API failed: " + e.getMessage());
        }
    }

    public ZoneDTO saveZone(ZoneDTO zoneDTO, String localToken) {
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new RuntimeException("Min temperature must be less than Max temperature.");
        }

        String deviceId;
        try {
            deviceId = registerDeviceWithExternalIoT(zoneDTO);
        } catch (Exception e) {
            System.err.println("IoT Registration failed: " + e.getMessage());
            deviceId = "DUMMY-" + System.currentTimeMillis();
        }

        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        zone.setDeviceId(deviceId);

        Zone savedZone = zoneRepository.save(zone);
        return modelMapper.map(savedZone, ZoneDTO.class);
    }

    private String registerDeviceWithExternalIoT(ZoneDTO zoneDTO) {
        String externalToken = getExternalIoTToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(externalToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("name", zoneDTO.getZoneName() + "-Sensor");
        body.put("zoneId", zoneDTO.getZoneName());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    IOT_API_BASE_URL + "/devices",
                    entity,
                    Map.class
            );

            if ((response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK)
                    && response.getBody() != null) {
                return (String) response.getBody().get("deviceId");
            }
            throw new RuntimeException("Device registration failed");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ZoneDTO> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(zone -> modelMapper.map(zone, ZoneDTO.class))
                .collect(Collectors.toList());
    }

    public ZoneDTO updateZone(Long id, ZoneDTO zoneDTO) {
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new RuntimeException("Min temperature must be less than Max temperature.");
        }

        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        modelMapper.map(zoneDTO, existingZone);
        existingZone.setId(id);
        return modelMapper.map(zoneRepository.save(existingZone), ZoneDTO.class);
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    public ZoneDTO getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        return modelMapper.map(zone, ZoneDTO.class);
    }
}
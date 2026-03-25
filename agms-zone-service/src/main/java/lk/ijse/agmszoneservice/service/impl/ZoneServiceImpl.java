package lk.ijse.agmszoneservice.service.impl;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import lk.ijse.agmszoneservice.entity.Zone;
import lk.ijse.agmszoneservice.repository.ZoneRepository;
import lk.ijse.agmszoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    private final String IOT_API_BASE_URL = "http://104.211.95.241:8080/api";

    @Override
    public ZoneDTO saveZone(ZoneDTO zoneDTO, String localToken) {
        validateTemperature(zoneDTO);

        String deviceId;
        try {
            deviceId = registerDeviceWithExternalIoT(zoneDTO);
        } catch (Exception e) {
            log.error("EXTERNAL API OFFLINE: Generating simulated Device ID. Error: {}", e.getMessage());
            // Use a UUID so the system remains functional for the 'Automation Service' logic
            deviceId = "SIM-" + java.util.UUID.randomUUID().toString().substring(0, 8);
        }

        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        zone.setDeviceId(deviceId);

        Zone savedZone = zoneRepository.save(zone);
        return modelMapper.map(savedZone, ZoneDTO.class);
    }

    @Override
    public ZoneDTO getZoneByDeviceId(String deviceId) {
        log.info("Fetching zone configuration for Device ID: {}", deviceId);
        Zone zone = zoneRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("No zone found for device: " + deviceId));
        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public List<ZoneDTO> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(zone -> modelMapper.map(zone, ZoneDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ZoneDTO getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with ID: " + id));
        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public ZoneDTO updateZone(Long id, ZoneDTO zoneDTO) {
        validateTemperature(zoneDTO);
        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        modelMapper.map(zoneDTO, existingZone);
        existingZone.setId(id);
        return modelMapper.map(zoneRepository.save(existingZone), ZoneDTO.class);
    }

    @Override
    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    private void validateTemperature(ZoneDTO dto) {
        if (dto.getMinTemp() >= dto.getMaxTemp()) {
            throw new RuntimeException("Validation Error: Min temperature must be less than Max temperature.");
        }
    }

    private String getExternalIoTToken() {
        try {
            log.info("Attempting IoT Login...");
            Map<String, String> loginRequest = Map.of("username", "username", "password", "123456");

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    IOT_API_BASE_URL + "/auth/login", loginRequest, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("accessToken");
            }
        } catch (Exception e) {
            throw new RuntimeException("IoT Auth Server Unreachable");
        }
        throw new RuntimeException("Auth Failed");
    }

    private String registerDeviceWithExternalIoT(ZoneDTO zoneDTO) {
        String externalToken = getExternalIoTToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(externalToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "name", zoneDTO.getZoneName() + "-Sensor",
                "zoneId", zoneDTO.getZoneName()
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        log.info("Registering device with Body: {}", body);

        ResponseEntity<Map> response = restTemplate.postForEntity(IOT_API_BASE_URL + "/devices", entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Full External Response Body: {}", response.getBody());

            Object deviceId = response.getBody().get("deviceId");
            if (deviceId == null) {
                deviceId = response.getBody().get("id");
            }

            return deviceId != null ? deviceId.toString() : null;
        }
        throw new RuntimeException("External Device Registration Failed - Status: " + response.getStatusCode());
    }
}
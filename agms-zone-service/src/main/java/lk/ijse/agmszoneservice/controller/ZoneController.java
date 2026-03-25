package lk.ijse.agmszoneservice.controller;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import lk.ijse.agmszoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDTO> createZone(
            @RequestBody ZoneDTO zoneDTO,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(zoneService.saveZone(zoneDTO, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDTO> getZone(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<ZoneDTO> getZoneByDeviceId(@PathVariable String deviceId) {
        return ResponseEntity.ok(zoneService.getZoneByDeviceId(deviceId));
    }

    @GetMapping
    public ResponseEntity<List<ZoneDTO>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDTO> update(@PathVariable Long id, @RequestBody ZoneDTO zoneDTO) {
        return ResponseEntity.ok(zoneService.updateZone(id, zoneDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
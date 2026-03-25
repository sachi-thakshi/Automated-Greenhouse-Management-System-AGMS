package lk.ijse.agmszoneservice.controller;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import lk.ijse.agmszoneservice.entity.Zone;
import lk.ijse.agmszoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDTO> createZone(@RequestBody ZoneDTO zoneDTO) {
        return ResponseEntity.ok(zoneService.saveZone(zoneDTO));
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
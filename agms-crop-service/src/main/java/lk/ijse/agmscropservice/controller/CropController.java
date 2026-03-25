package lk.ijse.agmscropservice.controller;

import lk.ijse.agmscropservice.dto.CropDTO;
import lk.ijse.agmscropservice.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropService cropService;

    @PostMapping
    public ResponseEntity<CropDTO> saveCrop(@RequestBody CropDTO cropDTO) {
        return new ResponseEntity<>(cropService.saveCrop(cropDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropDTO> getCropById(@PathVariable String id) {
        return ResponseEntity.ok(cropService.getCropById(id));
    }

    @GetMapping
    public ResponseEntity<List<CropDTO>> getAllCrops() {
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<CropDTO>> getCropsByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(cropService.getCropsByZone(zoneId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCrop(@PathVariable String id, @RequestBody CropDTO cropDTO) {
        cropService.updateCrop(id, cropDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable String id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}
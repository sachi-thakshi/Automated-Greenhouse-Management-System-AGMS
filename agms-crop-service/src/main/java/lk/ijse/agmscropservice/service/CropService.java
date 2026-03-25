package lk.ijse.agmscropservice.service;

import lk.ijse.agmscropservice.dto.CropDTO;
import java.util.List;

public interface CropService {
    CropDTO saveCrop(CropDTO cropDTO);
    CropDTO getCropById(String id);
    List<CropDTO> getAllCrops();
    List<CropDTO> getCropsByZone(Long zoneId);
    void updateCrop(String id, CropDTO cropDTO);
    void deleteCrop(String id);
}
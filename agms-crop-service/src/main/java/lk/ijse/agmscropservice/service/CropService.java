package lk.ijse.agmscropservice.service;

import lk.ijse.agmscropservice.dto.CropDTO;
import java.util.List;

public interface CropService {
    CropDTO saveCrop(CropDTO cropDTO);
    void updateStatus(String id, String newStatus);
    List<CropDTO> getAllCrops();
    CropDTO getCropById(String id);
    void deleteCrop(String id);
}
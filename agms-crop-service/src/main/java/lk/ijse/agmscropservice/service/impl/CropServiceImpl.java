package lk.ijse.agmscropservice.service.impl;

import lk.ijse.agmscropservice.dto.CropDTO;
import lk.ijse.agmscropservice.entity.Crop;
import lk.ijse.agmscropservice.repository.CropRepository;
import lk.ijse.agmscropservice.service.CropService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    private final ModelMapper modelMapper;

    @Override
    public CropDTO saveCrop(CropDTO cropDTO) {
        Crop crop = modelMapper.map(cropDTO, Crop.class);
        return modelMapper.map(cropRepository.save(crop), CropDTO.class);
    }

    @Override
    public CropDTO getCropById(String id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + id));
        return modelMapper.map(crop, CropDTO.class);
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CropDTO> getCropsByZone(Long zoneId) {
        return cropRepository.findByZoneId(zoneId).stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateCrop(String id, CropDTO cropDTO) {
        Crop existingCrop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        // Manual map to ensure ID doesn't change
        existingCrop.setCropName(cropDTO.getCropName());
        existingCrop.setScientificName(cropDTO.getScientificName());
        existingCrop.setCategory(cropDTO.getCategory());
        existingCrop.setGrowthStatus(cropDTO.getGrowthStatus());
        existingCrop.setCropSeason(cropDTO.getCropSeason());
        existingCrop.setZoneId(cropDTO.getZoneId());

        cropRepository.save(existingCrop);
    }

    @Override
    public void deleteCrop(String id) {
        if (!cropRepository.existsById(id)) {
            throw new RuntimeException("Crop not found");
        }
        cropRepository.deleteById(id);
    }
}
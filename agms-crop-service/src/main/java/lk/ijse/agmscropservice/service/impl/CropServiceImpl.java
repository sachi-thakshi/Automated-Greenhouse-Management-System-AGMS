package lk.ijse.agmscropservice.service.impl;

import lk.ijse.agmscropservice.dto.CropDTO;
import lk.ijse.agmscropservice.entity.Crop;
import lk.ijse.agmscropservice.repository.CropRepository;
import lk.ijse.agmscropservice.service.CropService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        // Business Logic: New batches always start as SEEDLING
        crop.setStatus("SEEDLING");
        crop.setPlantedDate(LocalDate.now());
        return modelMapper.map(cropRepository.save(crop), CropDTO.class);
    }

    @Override
    public void updateStatus(String id, String newStatus) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop Batch not found: " + id));

        String currentStatus = crop.getStatus().toUpperCase();
        String targetStatus = newStatus.toUpperCase();

        // Business Logic: State Machine Transitions
        // SEEDLING -> VEGETATIVE -> HARVESTED
        if (isValidTransition(currentStatus, targetStatus)) {
            crop.setStatus(targetStatus);
            cropRepository.save(crop);
        } else {
            throw new IllegalArgumentException("Invalid state transition from " + currentStatus + " to " + targetStatus);
        }
    }

    private boolean isValidTransition(String current, String next) {
        switch (current) {
            case "SEEDLING":
                return next.equals("VEGETATIVE");
            case "VEGETATIVE":
                return next.equals("HARVESTED");
            default:
                return false; // HARVESTED is the final state
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CropDTO getCropById(String id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));
        return modelMapper.map(crop, CropDTO.class);
    }

    @Override
    public void deleteCrop(String id) {
        cropRepository.deleteById(id);
    }
}
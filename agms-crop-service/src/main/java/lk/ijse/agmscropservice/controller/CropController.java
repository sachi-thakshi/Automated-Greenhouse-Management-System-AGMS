package lk.ijse.agmscropservice.controller;

import lk.ijse.agmscropservice.entity.Crop;
import lk.ijse.agmscropservice.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/crops")
public class CropController {

    @Autowired
    private CropRepository cropRepository;

    // Save Crop
    @PostMapping
    public Crop saveCrop(@RequestBody Crop crop){
        return cropRepository.save(crop);
    }

    // Get All Crops
    @GetMapping
    public List<Crop> getAllCrops(){
        return cropRepository.findAll();
    }

    // Get Crop by ID
    @GetMapping("/{id}")
    public Optional<Crop> getCropById(@PathVariable String id){
        return cropRepository.findById(id);
    }

    // Update Crop
    @PutMapping("/{id}")
    public Crop updateCrop(@PathVariable String id, @RequestBody Crop crop){

        Crop existingCrop = cropRepository.findById(id).orElse(null);

        if(existingCrop != null){
            existingCrop.setCropName(crop.getCropName());
            existingCrop.setSeason(crop.getSeason());
            existingCrop.setFieldLocation(crop.getFieldLocation());

            return cropRepository.save(existingCrop);
        }

        return null;
    }

    // Delete Crop
    @DeleteMapping("/{id}")
    public String deleteCrop(@PathVariable String id){
        cropRepository.deleteById(id);
        return "Crop Deleted Successfully";
    }

}

package lk.ijse.agmszoneservice.service;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import lk.ijse.agmszoneservice.entity.Zone;
import lk.ijse.agmszoneservice.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ModelMapper modelMapper;

    public ZoneDTO saveZone(ZoneDTO zoneDTO) {
        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        Zone savedZone = zoneRepository.save(zone);
        return modelMapper.map(savedZone, ZoneDTO.class);
    }

    public List<ZoneDTO> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(zone -> modelMapper.map(zone, ZoneDTO.class))
                .collect(Collectors.toList());
    }

    public ZoneDTO updateZone(Long id, ZoneDTO zoneDTO) {
        if (!zoneRepository.existsById(id)) {
            throw new RuntimeException("Zone not found!");
        }
        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        zone.setId(id);
        return modelMapper.map(zoneRepository.save(zone), ZoneDTO.class);
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }
}

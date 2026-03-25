package lk.ijse.agmszoneservice.service;

import lk.ijse.agmszoneservice.dto.ZoneDTO;
import java.util.List;

public interface ZoneService {
    ZoneDTO saveZone(ZoneDTO zoneDTO, String localToken);
    List<ZoneDTO> getAllZones();
    ZoneDTO getZoneById(Long id);
    ZoneDTO updateZone(Long id, ZoneDTO zoneDTO);
    void deleteZone(Long id);

    ZoneDTO getZoneByDeviceId(String deviceId);
}
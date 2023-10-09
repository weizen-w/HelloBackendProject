package spring.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.domain.City;
import spring.controller.dto.CityDTO;
import spring.repository.CityRepository;

/**
 * 14/09/2023 HelloREST
 *
 * @author Wladimir Weizen
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CityService {

  private final CityRepository cityRepository;

  public List<CityDTO> findAll() {
    List<City> cities = cityRepository.findAll();
    List<CityDTO> cityDTOList = new ArrayList<>();
    cities.forEach(c -> cityDTOList.add(CityDTO.getInstance(c)));
    log.info("Reading {} records from City table.", cityDTOList.size());
    return cityDTOList;
  }

  public City findByName(String title) {
    City city = cityRepository.findByName(title);
    if (city == null) {
      city = new City(null, title);
      city = cityRepository.save(city);
    }
    return city;
  }

  public CityDTO findById(Integer id) {
    log.info("Get {} for the method", id);
    City city = cityRepository.findById(id).orElse(null);
    if (city != null) {
      CityDTO cityDTO = CityDTO.getInstance(city);
      log.info("Reading {} from City table.", cityDTO);
      return cityDTO;
    }
    log.error("Not found city, cityId={} from City table.", id);
    return null;
  }

  public CityDTO add(CityDTO cityDTO) {
    log.info("Get {} for the method", cityDTO);
    if (cityDTO != null) {
      City city = new City(null, cityDTO.getName());
      city = cityRepository.save(city);
      log.info("Save {} to City table", city);
      return CityDTO.getInstance(city);
    }
    log.debug("City is null");
    return null;
  }

  public CityDTO update(CityDTO cityDTO) {
    log.info("Get {} for the method", cityDTO);
    City city = cityRepository.findById(cityDTO.getId()).orElse(null);
    if (city != null) {
      city.setName(cityDTO.getName());
      city = cityRepository.save(city);
      log.info("Update city from City table: {} -> {}", city, cityDTO);
      return CityDTO.getInstance(city);
    }
    log.debug("City is null or not found");
    return null;
  }

  public CityDTO delete(Integer id) {
    log.info("Get {} for the method", id);
    City city = cityRepository.findById(id).orElse(null);
    if (city != null) {
      cityRepository.delete(city);
      log.info("Delete {} from City table", city);
      return CityDTO.getInstance(city);
    }
    log.debug("City is null or not found");
    return null;
  }
}

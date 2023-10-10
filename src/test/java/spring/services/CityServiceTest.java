package spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import spring.DTOClassesTest;
import spring.controller.dto.CityDTO;
import spring.domain.City;
import spring.repository.CityRepository;
import spring.service.CityService;

/**
 * 10/10/2023 HelloBackendProject
 *
 * @author Wladimir Weizen
 */
@SpringBootTest
@Nested
@DisplayName("City service is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class CityServiceTest {

  @Autowired
  private CityService cityService;
  @Autowired
  private CityRepository cityRepository;

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void add_City_test() {
    CityDTO cityDTOIn = new CityDTO(null, DTOClassesTest.CITY);
    CityDTO cityDTOOut = cityService.add(cityDTOIn);

    Assertions.assertNotNull(cityDTOOut.getId());
    Assertions.assertEquals(cityDTOIn.getName(), cityDTOOut.getName());

    City city = cityRepository.findById(cityDTOOut.getId()).orElse(null);
    Assertions.assertNotNull(city);
    Assertions.assertEquals(DTOClassesTest.CITY, city.getName());
  }

  @Test
  @Sql(scripts = "/sql/data.sql")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void delete_City_test() {
    CityDTO cityDTO = cityService.delete(3);
    CityDTO cityDTONull = cityService.findById(3);

    Assertions.assertEquals(3, cityDTO.getId());
    Assertions.assertEquals("Prague", cityDTO.getName());
    Assertions.assertNull(cityDTONull);
  }

  @Test
  @Sql(scripts = "/sql/data.sql")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void update_City_test() {
    CityDTO newCityDTO = new CityDTO(1, "Amsterdam");
    CityDTO cityDTO = cityService.update(newCityDTO);

    Assertions.assertEquals(newCityDTO.getId(), cityDTO.getId());
    Assertions.assertEquals(newCityDTO.getName(), cityDTO.getName());
  }
}

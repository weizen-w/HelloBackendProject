package spring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import spring.domain.City;

/**
 * 19/09/2023 HelloDTO
 *
 * @author Wladimir Weizen
 */

@AllArgsConstructor
@Data
public class CityDTO {

  private Integer id;
  private String name;

  public static CityDTO getInstance(City city) {
    return new CityDTO(city.getId(), city.getName());
  }
}

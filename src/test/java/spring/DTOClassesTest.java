package spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.controller.dto.CityDTO;
import spring.controller.dto.EventDTO;
import spring.domain.City;
import spring.domain.Event;

/**
 * 05/10/2023 HelloTest
 *
 * @author Wladimir Weizen
 */
public class DTOClassesTest {

  public static final int ID = 1;
  public static final String EVENT = "Jazz Concert";
  public static final String CITY = "Berlin";

  @Test
  public void testCityDTO() {
    City city = new City(ID, CITY);
    CityDTO cityDTO = CityDTO.getInstance(city);

    Assertions.assertEquals(ID, cityDTO.getId());
    Assertions.assertEquals(CITY, cityDTO.getName());
  }

  @Test
  public void testEventDTO() {
    City city = new City(ID, CITY);
    Event event = new Event(ID, EVENT, city);
    EventDTO eventDTO = EventDTO.getInstance(event);

    Assertions.assertEquals(ID, eventDTO.getId());
    Assertions.assertEquals(EVENT, eventDTO.getName());
    Assertions.assertEquals(CITY, eventDTO.getCity());
  }
}

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
import spring.controller.dto.EventDTO;
import spring.domain.Event;
import spring.repository.EventRepository;
import spring.service.EventService;

/**
 * 05/10/2023 HelloTest
 *
 * @author Wladimir Weizen
 */

@SpringBootTest
@Nested
@DisplayName("Event service is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class EventServiceTest {

  @Autowired
  private EventService eventService;
  @Autowired
  private EventRepository eventRepository;

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void add_Event_test() {
    EventDTO eventDTOIn = new EventDTO(null, DTOClassesTest.EVENT, DTOClassesTest.CITY);
    EventDTO eventDTOOut = eventService.add(eventDTOIn);

    Assertions.assertNotNull(eventDTOOut.getId());
    Assertions.assertEquals(eventDTOIn.getName(), eventDTOOut.getName());
    Assertions.assertEquals(eventDTOIn.getCity(), eventDTOOut.getCity());

    Event event = eventRepository.findById(eventDTOOut.getId()).orElse(null);
    Assertions.assertNotNull(event);
    Assertions.assertEquals(DTOClassesTest.EVENT, event.getName());
    Assertions.assertEquals(DTOClassesTest.CITY, event.getCity().getName());
  }

  @Test
  @Sql(scripts = "/sql/data.sql")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void delete_Event_test() {
    EventDTO eventDTO = eventService.delete(1);
    EventDTO eventDTONull = eventService.findById(1);

    Assertions.assertEquals(1, eventDTO.getId());
    Assertions.assertEquals("Jazz Concert", eventDTO.getName());
    Assertions.assertEquals("Paris", eventDTO.getCity());
    Assertions.assertNull(eventDTONull);
  }

  @Test
  @Sql(scripts = "/sql/data.sql")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void update_Event_test() {
    EventDTO newEventDTO = new EventDTO(1, "Ice show", "Amsterdam");
    EventDTO eventDTO = eventService.update(newEventDTO);

    Assertions.assertEquals(newEventDTO.getId(), eventDTO.getId());
    Assertions.assertEquals(newEventDTO.getName(), eventDTO.getName());
  }
}

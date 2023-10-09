package spring.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import spring.domain.Event;

/**
 * 19/09/2023 HelloDTO
 *
 * @author Wladimir Weizen
 */

@AllArgsConstructor
@Data
public class EventDTO {

  private Integer id;
  private String name;
  private String city;

  public static EventDTO getInstance(Event event) {
    return new EventDTO(event.getId(), event.getName(), event.getCity().getName());
  }
}

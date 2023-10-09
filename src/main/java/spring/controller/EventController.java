package spring.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.dto.EventDTO;
import spring.service.EventService;

/**
 * 14/09/2023 HelloREST
 *
 * @author Wladimir Weizen
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

  private final EventService eventService;

  @GetMapping("/all")
  public List<EventDTO> findAll() {
    return eventService.findAll();
  }

  @GetMapping("/{id}")
  public EventDTO findById(@PathVariable Integer id) {
    return eventService.findById(id);
  }

  @PostMapping("/add")
  public EventDTO add(@RequestBody EventDTO eventDTO) {
    return eventService.add(eventDTO);
  }

  @PutMapping("/update")
  public EventDTO update(@RequestBody EventDTO eventDTO) {
    return eventService.update(eventDTO);
  }

  @DeleteMapping("/delete/{id}")
  public EventDTO delete(@PathVariable Integer id) {
    return eventService.delete(id);
  }
}

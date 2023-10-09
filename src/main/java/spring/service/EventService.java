package spring.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.domain.Event;
import spring.controller.dto.EventDTO;
import spring.repository.EventRepository;

/**
 * 14/09/2023 HelloREST
 *
 * @author Wladimir Weizen
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

  private final EventRepository eventRepository;
  private final CityService cityService;

  public List<EventDTO> findAll() {
    List<Event> events = eventRepository.findAll();
    List<EventDTO> eventDTOList = new ArrayList<>();
    events.forEach(e -> eventDTOList.add(EventDTO.getInstance(e)));
    log.info("Reading {} records from Event table.", eventDTOList.size());
    return eventDTOList;
  }

  public EventDTO findById(Integer id) {
    log.info("Get {} for the method", id);
    Event event = eventRepository.findById(id).orElse(null);
    if (event != null) {
      EventDTO eventDTO = EventDTO.getInstance(event);
      log.info("Reading {} from Event table.", eventDTO);
      return eventDTO;
    }
    log.error("Not found event, eventId={} from Event table.", id);
    return null;
  }

  public EventDTO add(EventDTO eventDTO) {
    log.info("Get {} for the method", eventDTO);
    if (eventDTO != null) {
      Event event = new Event(null, eventDTO.getName(), cityService.findByName(eventDTO.getCity()));
      event = eventRepository.save(event);
      log.info("Save {} to Event table", event);
      return EventDTO.getInstance(event);
    }
    log.debug("Event is null");
    return null;
  }

  public EventDTO update(EventDTO eventDTO) {
    log.info("Get {} for the method", eventDTO);
    Event event = eventRepository.findById(eventDTO.getId()).orElse(null);
    if (event != null) {
      event.setName(eventDTO.getName());
      event.setCity(cityService.findByName(eventDTO.getCity()));
      event = eventRepository.save(event);
      log.info("Update event from Event table: {} -> {}", event, eventDTO);
      return EventDTO.getInstance(event);
    }
    log.debug("Event is null or not found");
    return null;
  }

  public EventDTO delete(Integer id) {
    log.info("Get {} for the method", id);
    Event event = eventRepository.findById(id).orElse(null);
    if (event != null) {
      eventRepository.delete(event);
      log.info("Delete {} from Event table", event);
      return EventDTO.getInstance(event);
    }
    log.debug("Event is null or not found");
    return null;
  }
}

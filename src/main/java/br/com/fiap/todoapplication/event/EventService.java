package br.com.fiap.todoapplication.event;

import br.com.fiap.todoapplication.event.dto.EventFindDTO;
import br.com.fiap.todoapplication.event.dto.EventUpdateDTO;
import br.com.fiap.todoapplication.exception.NoSuchObjectException;
import br.com.fiap.todoapplication.user.User;
import br.com.fiap.todoapplication.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public List<EventFindDTO> findAllByUserId(Long userId){
        return eventRepository.findAllByUserId(userId)
                .stream().map(this::parseEventEFDTO).collect(Collectors.toList());
    }

    public Event findById(Long id) throws NoSuchObjectException {
        var Event = eventRepository.findById(id);

        if(Event.isEmpty())
            throw new NoSuchObjectException("Object with id".
                    concat(String.valueOf(id)).concat(" Not found"));

        return Event.get();
    }

    public void save(Event event, User user){
        event.setUser(user);
        eventRepository.save(event);
    }

    public void updateEvent(EventUpdateDTO eventDTO, Long eventId, Long userId) throws Exception {
        if (checkIfUserHasEvent(userId, eventId)){
            var eventInDB = findById(eventId);
            dataUpdater(eventInDB, eventDTO);
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    "doesn't have any Event registered with ID " + eventId);
        }
    }

    public void dataUpdater(Event eventInDb, EventUpdateDTO eventDTO) throws Exception {
        if(eventDTO.getTitle() != null)
            eventInDb.setTitle(eventDTO.getTitle());

        if(eventDTO.getDate() != null)
            eventInDb.setDate(DataUtils.parseStringToCalendar(eventDTO.getDate()));

        eventRepository.save(eventInDb);
    }

    public void delete(Long userId, Long eventId) throws NoSuchObjectException {
        if (checkIfUserHasEvent(userId, eventId)) {
            eventRepository.deleteById(eventId);
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    "doesn't have any Event registered with ID " + eventId);
        }
    }

    public void changeEventStatus(Long idEvent) throws NoSuchObjectException {
        var event = findById(idEvent);
        if (event.getStatus().equals("Active")){
            event.setStatus("Cancelled");
        }else {
            event.setStatus("Active");
        }
        eventRepository.save(event);
    }

    public EventFindDTO parseEventEFDTO(Event event) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        var date = sdf.format(event.getDate().getTime());
        return new EventFindDTO(event.getId(), event.getTitle(), date, event.getStatus());
    }

    public boolean checkIfUserHasEvent(Long userId, Long eventId) throws NoSuchObjectException {
        List<Event> userEvents = eventRepository.findAllByUserId(userId);
        boolean hasEvent = false;

        if (userEvents.isEmpty())
            throw new NoSuchObjectException("User with id " + userId + "doesn't have any Event registered");

        findById(eventId);

        for(Event event : userEvents){
            if(event.getId().equals(eventId)){
                hasEvent = true;
                break;
            }

        }
        if (hasEvent){
            return true;
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    " doesn't have event with ID " + eventId + " registered");
        }
    }
    
}

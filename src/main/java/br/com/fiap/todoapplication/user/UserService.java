package br.com.fiap.todoapplication.user;

import br.com.fiap.todoapplication.event.Event;
import br.com.fiap.todoapplication.event.EventService;
import br.com.fiap.todoapplication.event.dto.EventFindDTO;
import br.com.fiap.todoapplication.event.dto.EventInsertDTO;
import br.com.fiap.todoapplication.event.dto.EventUpdateDTO;
import br.com.fiap.todoapplication.exception.NoSuchObjectException;
import br.com.fiap.todoapplication.todo.Todo;
import br.com.fiap.todoapplication.todo.TodoService;
import br.com.fiap.todoapplication.todo.dto.TodoFindDTO;
import br.com.fiap.todoapplication.todo.dto.TodoInsertDTO;
import br.com.fiap.todoapplication.todo.dto.TodoUpdateDTO;
import br.com.fiap.todoapplication.user.dto.UserInsertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoService todoService;

    @Autowired
    EventService eventService;

    public User findById(Long userId) throws NoSuchObjectException {
        var user = userRepository.findById(userId);

        if(user.isEmpty())
            throw new NoSuchObjectException("User with id".concat(String.valueOf(userId)).concat(" Not found"));

        return user.get();
    }

    public User insertNewUser(UserInsertDTO userInsertDTO){
        var user = new User(userInsertDTO);
        return userRepository.save(user);
    }

    public void updateUser(UserInsertDTO userInsertDTO, Long userId) throws NoSuchObjectException {
        var userInDb = findById(userId);
        dataUpdater(userInDb, userInsertDTO);
    }

    public void deleteUserById(Long id) throws NoSuchObjectException {
        findById(id);
        userRepository.deleteById(id);
    }

    public List<TodoFindDTO> findAllUserToDos(Long id){
        return todoService.findAllByUserId(id);
    }

    public TodoFindDTO findTodoById(Long userId, Long todoId) throws NoSuchObjectException {
        if(todoService.checkIfUserHasTodo(userId, todoId)) {
            return todoService.parseTodoTFDTO(todoService.findById(todoId));
        }
        return null;
    }

    public boolean changeTodoDone(Long userId, Long todoId) throws NoSuchObjectException {
        if(todoService.checkIfUserHasTodo(userId, todoId)) {
            todoService.changeTodoDone(todoId);
            return true;
        } else {
            throw new RuntimeException("Error trying to change ToDo done");
        }
    }

    public void updateTodo(TodoUpdateDTO todoUpdateDTO, Long userId, Long todoId) throws NoSuchObjectException {
        todoService.updateTodo(todoUpdateDTO, todoId, userId);
    }

    public void insertNewTodo(TodoInsertDTO todoInsertDTO, Long userId) throws NoSuchObjectException {
        var user = findById(userId);
        var todo = new Todo(todoInsertDTO);
        todoService.save(todo, user);
        user.addTask(todo);
        userRepository.save(user);
    }

    public void deleteUserTodo(Long userId, Long todoID) throws NoSuchObjectException {
        todoService.delete(userId, todoID);
    }

    public List<EventFindDTO> findAllEventsByUserId(Long userId) throws NoSuchObjectException {
        findById(userId);
        return eventService.findAllByUserId(userId);
    }

    public EventFindDTO findEventById(Long userId, Long eventId) throws NoSuchObjectException, ParseException {
        if(eventService.checkIfUserHasEvent(userId, eventId)) {
            return eventService.parseEventEFDTO(eventService.findById(eventId));
        }
        return null;
    }

    public boolean changeEventStatus(Long userId, Long eventId) throws NoSuchObjectException {
        if(eventService.checkIfUserHasEvent(userId, eventId)) {
            eventService.changeEventStatus(eventId);
            return true;
        } else {
            throw new RuntimeException("Error trying to change event status");
        }
    }

    public void updateEvent(EventUpdateDTO eventUpdateDTO, Long userId, Long eventId) throws Exception {
        eventService.updateEvent(eventUpdateDTO, eventId, userId);
    }

    public void insertNewEvent(EventInsertDTO eventInsertDTO, Long userId) throws Exception {
        var user = findById(userId);
        var event = new Event(eventInsertDTO);
        eventService.save(event, user);
        user.addEvent(event);
        userRepository.save(user);
    }

    public void deleteUserEvent(Long userId, Long eventID) throws NoSuchObjectException {
        eventService.delete(userId, eventID);
    }

    public void dataUpdater(User userInDb, UserInsertDTO userInsertDTO){
        userInDb.setName(userInsertDTO.getName());
        if(userInsertDTO.getAvatarURL() == null)
            userInDb.setAvatarURL(userInDb.getAvatarURL());
        userRepository.save(userInDb);
    }
}

package br.com.fiap.todoapplication.user;

import br.com.fiap.todoapplication.event.dto.EventFindDTO;
import br.com.fiap.todoapplication.event.dto.EventInsertDTO;
import br.com.fiap.todoapplication.event.dto.EventUpdateDTO;
import br.com.fiap.todoapplication.exception.NoSuchObjectException;
import br.com.fiap.todoapplication.todo.dto.TodoFindDTO;
import br.com.fiap.todoapplication.todo.dto.TodoInsertDTO;
import br.com.fiap.todoapplication.todo.dto.TodoUpdateDTO;
import br.com.fiap.todoapplication.user.dto.UserInsertDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId) throws NoSuchObjectException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findById(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<User> insertNewUser(@RequestBody @Valid UserInsertDTO userInsertDTO) {
        return ResponseEntity.ok().body(userService.insertNewUser(userInsertDTO));
    }

    @PostMapping("/{userId}/edit")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserInsertDTO userInsertDTO,
                                           @PathVariable Long userId) throws NoSuchObjectException {
        userService.updateUser(userInsertDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) throws NoSuchObjectException {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userId}/ToDos")
    public ResponseEntity<List<TodoFindDTO>> findAllUserToDOs(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUserToDos(userId));
    }

    @GetMapping("/{userId}/ToDos/{todoId}")
    public ResponseEntity<TodoFindDTO> findTodoById(@PathVariable Long userId,
                                                    @PathVariable Long todoId) throws NoSuchObjectException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findTodoById(userId, todoId));
    }

    @PutMapping("/{userId}/ToDos/{todoId}/editDone")
    public ResponseEntity<Void> changeTodoDone(@PathVariable Long userId,
                                               @PathVariable Long todoId) throws NoSuchObjectException {
        System.out.println("Change done operation called");
        boolean changed = userService.changeTodoDone(userId, todoId);

        if (changed){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/{userId}/ToDos/{todoId}/edit")
    public ResponseEntity<Void> updateUserTodo(@PathVariable Long userId,
                                               @PathVariable Long todoId,
                                               @RequestBody @Valid TodoUpdateDTO todoUpdateDTO) throws NoSuchObjectException {
        userService.updateTodo(todoUpdateDTO, userId, todoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/ToDos/add")
    public ResponseEntity<Void> insertNewUserTodo(@RequestBody @Valid TodoInsertDTO todoInsertDTO,
                                                  @PathVariable Long userId) throws NoSuchObjectException {
        userService.insertNewTodo(todoInsertDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/ToDos/{todoId}")
    public ResponseEntity<Void> deleteUserTodo(@PathVariable Long userId, @PathVariable Long todoId) throws NoSuchObjectException {
        userService.deleteUserTodo(userId, todoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/Events")
    public ResponseEntity<List<EventFindDTO>> findAllUserEvents(@PathVariable Long userId) throws NoSuchObjectException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllEventsByUserId(userId));
    }

    @GetMapping("/{userId}/Events/{eventId}")
    public ResponseEntity<EventFindDTO> findUserEventById(@PathVariable Long userId, @PathVariable Long eventId) throws NoSuchObjectException, ParseException {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.findEventById(userId, eventId));
    }

    @PutMapping("/{userId}/Events/{eventId}/editStatus")
    public ResponseEntity<Void> changeEventStatus(@PathVariable Long userId,
                                                  @PathVariable Long eventId) throws NoSuchObjectException {
        boolean changed = userService.changeEventStatus(userId,eventId);

        if (changed){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/{userId}/Events/{eventId}/edit")
    public ResponseEntity<Void> updateUserEvent(@PathVariable Long userId,
                                                @PathVariable Long eventId,
                                                @RequestBody @Valid EventUpdateDTO eventUpdateDTO) throws Exception {
        userService.updateEvent(eventUpdateDTO, userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/Events/add")
    public ResponseEntity<Void> insertNewUserEvent(@RequestBody @Valid EventInsertDTO eventInsertDTO,
                                                   @PathVariable Long userId) throws Exception {
        userService.insertNewEvent(eventInsertDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/Events/{eventId}")
    public ResponseEntity<Void> deleteUserEvent(@PathVariable Long userId, @PathVariable Long eventId) throws NoSuchObjectException {
        System.out.println("Calling delete operation");
        userService.deleteUserEvent(userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

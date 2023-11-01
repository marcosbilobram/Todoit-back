package br.com.fiap.todoapplication.todo;

import br.com.fiap.todoapplication.exception.NoSuchObjectException;
import br.com.fiap.todoapplication.todo.dto.TodoFindDTO;
import br.com.fiap.todoapplication.todo.dto.TodoInsertDTO;
import br.com.fiap.todoapplication.todo.dto.TodoUpdateDTO;
import br.com.fiap.todoapplication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    public List<TodoFindDTO> findAllByUserId(Long userId){
        return todoRepository.findAllByUserId(userId)
                .stream().map(this::parseTodoTFDTO).collect(Collectors.toList());
    }

    public Todo findById(Long id) throws NoSuchObjectException {
        var todo = todoRepository.findById(id);

        if(todo.isEmpty())
            throw new NoSuchObjectException("Object with id".
                                            concat(String.valueOf(id)).concat(" Not found"));

        return todo.get();
    }

    public void save(Todo todo, User user){
        todo.setUser(user);
        todoRepository.save(todo);
    }

    public void updateTodo(TodoUpdateDTO todoDTO, Long todoId, Long userId) throws NoSuchObjectException {
        if (checkIfUserHasTodo(userId, todoId)){
            var todoInDB = findById(todoId);
            dataUpdater(todoInDB, todoDTO);
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    "doesn't have any ToDo registered with ID " + todoId);
        }
    }

    public void dataUpdater(Todo todoInDb, TodoUpdateDTO todoDTO){
        if (todoDTO.getName() != null)
            todoInDb.setName(todoDTO.getName());

        if (todoDTO.getDescription() != null)
            todoInDb.setDescription(todoDTO.getDescription());

        todoRepository.save(todoInDb);
    }

    public void delete(Long userId, Long todoId) throws NoSuchObjectException {
        if (checkIfUserHasTodo(userId, todoId)) {
            todoRepository.deleteById(todoId);
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    "doesn't have any ToDo registered with ID " + todoId);
        }
    }

    public void changeTodoDone(Long idTodo) throws NoSuchObjectException {
        var todo = findById(idTodo);
        todo.setDone(!todo.getDone());
        todoRepository.save(todo);
    }

    public TodoFindDTO parseTodoTFDTO(Todo todo) {
        return new TodoFindDTO(todo.getId(), todo.getName(), todo.getDescription(), todo.getDone());
    }

    public boolean checkIfUserHasTodo(Long userId, Long todoId) throws NoSuchObjectException {
        List<Todo> userTodos = todoRepository.findAllByUserId(userId);
        boolean hasTodo = false;

        if (userTodos.isEmpty())
            throw new NoSuchObjectException("User with id " + userId + "doesn't have any ToDo registered");

        findById(todoId);

        for(Todo td : userTodos){
            if (td.getId().equals(todoId)) {
                hasTodo = true;
                break;
            }
        }
        if (hasTodo){
            return true;
        } else {
            throw new NoSuchObjectException("User with id " + userId +
                    " doesn't have ToDo with ID " + todoId + " registered");
        }
    }
}

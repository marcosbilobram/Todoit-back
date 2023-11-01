package br.com.fiap.todoapplication.todo;

import br.com.fiap.todoapplication.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByUserId(Long id);
}

package br.com.fiap.todoapplication.config;

import br.com.fiap.todoapplication.event.Event;
import br.com.fiap.todoapplication.event.EventRepository;
import br.com.fiap.todoapplication.event.EventService;
import br.com.fiap.todoapplication.todo.Todo;
import br.com.fiap.todoapplication.todo.TodoRepository;
import br.com.fiap.todoapplication.user.User;
import br.com.fiap.todoapplication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.List;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().id(1L).name("Marcos").avatarURL("www.avatar.url").build();

        userRepository.save(user);

        Todo todo = Todo.builder().id(1L).name("Projeto fns")
                .description("Terminar o projeto").user(user).build();

        Todo todo2 = Todo.builder().id(2L).name("Dormir")
                .description("Dormir 7 horas seguidas").user(user).build();

        Todo todo3 = Todo.builder().id(3L).name("Comer")
                .description("Comer bem").user(user).build();

        Todo todo4 = Todo.builder().id(4L).name("Viver")
                .description("Viver fora dos códigos um pouco").user(user).build();

        Todo todo5 = Todo.builder().id(5L).name("Respirar")
                .description("Respirar").user(user).build();

        todoRepository.saveAll(List.of(todo, todo2, todo3, todo4, todo5));

        Event event = Event.builder().id(1L).title("Dormir").date(Calendar.getInstance()).user(user).build();

        Event event2 = Event.builder().id(2L).title("Dormir 1").date(Calendar.getInstance()).user(user).build();

        Event event3 = Event.builder().id(3L).title("Dormir 2").date(Calendar.getInstance()).user(user).build();

        Event event4 = Event.builder().id(4L).title("Dormir 3").date(Calendar.getInstance()).user(user).build();

        Event event5 = Event.builder().id(5L).title("Dormir 4").date(Calendar.getInstance()).user(user).build();

        eventRepository.saveAll(List.of(event, event2, event3, event4, event5));
    }
}

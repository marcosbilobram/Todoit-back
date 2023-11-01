package br.com.fiap.todoapplication.user;

import br.com.fiap.todoapplication.event.Event;
import br.com.fiap.todoapplication.todo.Todo;
import br.com.fiap.todoapplication.user.dto.UserInsertDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_user")
@SequenceGenerator(name = "user", sequenceName = "SQ_TB_USER", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String name;

    private String avatarURL;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Todo> todos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Event> events;

    public void addTask(Todo todo) {
        this.todos.add(todo);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public User(UserInsertDTO dto){
        this.name = dto.getName();
        this.avatarURL = dto.getAvatarURL();
    }
}

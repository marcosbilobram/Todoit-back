package br.com.fiap.todoapplication.todo;

import br.com.fiap.todoapplication.todo.dto.TodoFindDTO;
import br.com.fiap.todoapplication.todo.dto.TodoInsertDTO;
import br.com.fiap.todoapplication.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_todo")
@SequenceGenerator(name = "todo", sequenceName = "SQ_TB_TODO", allocationSize = 1)
public class Todo {

    @Id
    @GeneratedValue(generator = "todo", strategy = GenerationType.SEQUENCE)
    @Column(name = "todo_id")
    private Long id;

    @Column(name = "todo_name", nullable = false, length = 45)
    private String name;

    @Column(name = "todo_desc", nullable = false)
    private String description;

    @Builder.Default
    private Boolean done = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Todo(TodoInsertDTO todoDTO) {
        this.name = todoDTO.getName();
        this.description = todoDTO.getDescription();
        this.done = false;
    }

    public Todo(TodoFindDTO todoFindDTO) {
        this.name = todoFindDTO.getName();
        this.description = todoFindDTO.getDescription();
    }

}

package br.com.fiap.todoapplication.todo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TodoFindDTO {

    private Long id;

    private String name;

    private String description;

    private Boolean done;

}

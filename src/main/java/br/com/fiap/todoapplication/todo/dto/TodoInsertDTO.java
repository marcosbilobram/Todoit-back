package br.com.fiap.todoapplication.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TodoInsertDTO {

    @NotNull @NotBlank @Size(min = 1, max = 45)
    private String name;

    @Size(min = 1, max = 75)
    private String description;
}

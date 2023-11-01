package br.com.fiap.todoapplication.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInsertDTO {

    @NotNull @NotBlank @Size(min = 5, max = 45)
    private String name;

    private String avatarURL;

}

package br.com.fiap.todoapplication.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;

@AllArgsConstructor
@Data
public class EventInsertDTO {

    @NotNull @NotBlank @Size(min = 1, max = 20)
    private String title;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    @NotNull
    private String date;

    /*@Pattern(regexp = "^(Active|Cancelled)$", message = "O status deve ser 'Active' ou 'Cancelled'")
    private String status; */
}

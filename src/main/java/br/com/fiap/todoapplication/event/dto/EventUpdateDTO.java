package br.com.fiap.todoapplication.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EventUpdateDTO {

    @Size(min = 1, max = 20)
    private String title;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String date;

}

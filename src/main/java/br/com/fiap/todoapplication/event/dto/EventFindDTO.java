package br.com.fiap.todoapplication.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Calendar;

@AllArgsConstructor
@Data
public class EventFindDTO {

    private Long id;

    private String title;

    private String date;

    private String status;

}

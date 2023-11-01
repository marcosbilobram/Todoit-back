package br.com.fiap.todoapplication.event;

import br.com.fiap.todoapplication.event.dto.EventInsertDTO;
import br.com.fiap.todoapplication.user.User;
import br.com.fiap.todoapplication.utils.DataUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_event")
@SequenceGenerator(name = "event", sequenceName = "SQ_TB_EVENT", allocationSize = 1)
@Check(constraints = "evnt_status in ('Active', 'Cancelled')")
public class Event {

    @Id
    @GeneratedValue(generator = "event", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "evnt_title", length = 25, nullable = false)
    private String title;

    //@CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Calendar date;

    @Column(name = "evnt_status", nullable = false, length = 10)
    @Builder.Default
    private String status = "Active";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Event(EventInsertDTO eventDTO) throws Exception {
        this.title = eventDTO.getTitle();
        this.date = DataUtils.parseStringToCalendar(eventDTO.getDate());
        this.status = "Active";
    }

}

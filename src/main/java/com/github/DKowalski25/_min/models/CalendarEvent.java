package com.github.DKowalski25._min.models;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_events")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Builder
public class CalendarEvent {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_event", nullable = false)
    private LocalDateTime startEvent;

    @Column(name = "end_event", nullable = false)
    private LocalDateTime endEvent;

//    private String recurringRule; Для повторяющихся событий, реализуем чуть позже

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

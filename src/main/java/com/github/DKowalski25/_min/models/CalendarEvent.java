package com.github.DKowalski25._min.models;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_event")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

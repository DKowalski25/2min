package com.github.DKowalski25._min.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"timeBlock"})
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Column(name = "is_done")
    private boolean done;

    @Column(name = "is_planned_for_next")
    private boolean plannedForNext = false;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @Column(name = "period_marker")
    private String periodMarker;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(
            name = "time_block_id",
            nullable = false
    )
    private TimeBlock timeBlock;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

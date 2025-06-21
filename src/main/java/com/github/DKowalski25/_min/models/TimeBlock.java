package com.github.DKowalski25._min.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "time_blocks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TimeType type;

    @OneToMany(mappedBy = "timeBlock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}

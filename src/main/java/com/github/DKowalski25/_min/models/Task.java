package com.github.DKowalski25._min.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Column(name = "is_done")
    private boolean done;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_block_type")  // Связь через тип (не через ID)
    private TimeType timeBlockType;

    @ManyToOne
    @JoinColumn(
            name = "time_block_type",       // Ссылаемся на type в TimeBlock
            referencedColumnName = "type",  // Указываем, что связь по полю type
            insertable = false,            // Запрещаем автоматическую вставку
            updatable = false              // Запрещаем автоматическое обновление
    )
    private TimeBlock timeBlock;
}

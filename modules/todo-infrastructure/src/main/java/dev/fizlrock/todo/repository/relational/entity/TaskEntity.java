package dev.fizlrock.todo.repository.relational.entity;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

/** TaskEntity */
public record TaskEntity(
    @Id UUID id,
    UUID projectId,
    String name,
    @Column("planned_end_date") LocalDate finishDate,
    @Column("completed") boolean completed) {}

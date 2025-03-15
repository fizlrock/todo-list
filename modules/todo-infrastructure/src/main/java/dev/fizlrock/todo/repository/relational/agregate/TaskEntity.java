package dev.fizlrock.todo.repository.relational.agregate;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("task")
public record TaskEntity(
    @Id UUID id,
    @Column("project_id") UUID projectId,
    String name,
    boolean completed,
    @Column("planned_end_date") LocalDate finishDate) {}

package dev.fizlrock.todo.repository.relational.agregate;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/** ProjectAgregate */
@Table("project")
public record ProjectAgregate(
    @Id UUID id,
    @MappedCollection(idColumn = "project_id") Set<TaskEntity> tasks,
    String name,
    String description,
    @Column("start_time") LocalDate startDate,
    @Column("end_time") LocalDate endDate) {}

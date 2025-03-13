package dev.fizlrock.todo.repository.relational.mapper;

import dev.fizlrock.todo.domain.entity.Task;
import dev.fizlrock.todo.repository.relational.entity.TaskEntity;
import org.mapstruct.Mapper;

/** TaskEntityMapper */
@Mapper(componentModel = "spring")
public interface TaskEntityMapper {

  TaskEntity toEntity(Task t);
}

package dev.fizlrock.todo.repository.relational.mapper;

import dev.fizlrock.todo.domain.entity.Task;
import dev.fizlrock.todo.repository.relational.agregate.TaskEntity;
import org.mapstruct.Mapper;

/** TaskEntityMapper */
@Mapper(componentModel = "spring")
public interface TaskEntityMapper {

  TaskEntity toEntity(Task t);

  default Task toDomain(TaskEntity t) {
    return Task.loadFromDatabase(t.id(), t.projectId(), t.name(), t.finishDate(), t.completed());
  }
}

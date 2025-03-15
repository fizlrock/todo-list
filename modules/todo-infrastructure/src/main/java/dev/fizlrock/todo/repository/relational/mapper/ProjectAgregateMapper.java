package dev.fizlrock.todo.repository.relational.mapper;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.entity.Task;
import dev.fizlrock.todo.repository.relational.agregate.ProjectAgregate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    uses = {TaskEntityMapper.class})
public interface ProjectAgregateMapper {

  TaskEntityMapper taskMapper = Mappers.getMapper(TaskEntityMapper.class);

  ProjectAgregate toAgregate(Project p);

  default Project toDomain(ProjectAgregate p) {

    Map<UUID, Task> tasks = new HashMap<>();
    p.tasks().stream().map(taskMapper::toDomain).forEach(t -> tasks.put(t.getId(), t));
    return Project.loadFromDatabase(
        p.id(), p.name(), p.description(), p.startDate(), p.endDate(), tasks);
  }
}

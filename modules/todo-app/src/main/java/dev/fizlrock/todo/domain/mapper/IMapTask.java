package dev.fizlrock.todo.domain.mapper;

import dev.fizlrock.todo.domain.dto.TaskMsg;
import dev.fizlrock.todo.domain.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** IMapTask */
@Mapper(componentModel = "spring")
public interface IMapTask {
  @Mapping(source = "finishDate", target = "date")
  TaskMsg toDto(Task t);
}

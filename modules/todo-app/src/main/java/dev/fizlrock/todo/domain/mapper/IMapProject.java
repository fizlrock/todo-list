package dev.fizlrock.todo.domain.mapper;

import dev.fizlrock.todo.domain.dto.ProjectMsg;
import dev.fizlrock.todo.domain.entity.Project;
import org.mapstruct.Mapper;

/** IMapProject */
@Mapper(componentModel = "spring")
public interface IMapProject {

  ProjectMsg toDto(Project p);
}

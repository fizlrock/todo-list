package dev.fizlrock.todo.domain.mapper;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.domain.service.dto.ProjectMsg;
import org.mapstruct.Mapper;

/** IMapProject */
@Mapper(componentModel = "spring")
public interface IMapProject {

  ProjectMsg toDto(Project p);
}

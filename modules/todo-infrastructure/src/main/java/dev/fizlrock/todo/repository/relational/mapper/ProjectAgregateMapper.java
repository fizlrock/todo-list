package dev.fizlrock.todo.repository.relational.mapper;

import dev.fizlrock.todo.domain.entity.Project;
import dev.fizlrock.todo.repository.relational.entity.ProjectAgregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {TaskEntityMapper.class})
public interface ProjectAgregateMapper {

  @Mapping(source = "new", target = "isNew")
  ProjectAgregate toAgregate(Project p);
}

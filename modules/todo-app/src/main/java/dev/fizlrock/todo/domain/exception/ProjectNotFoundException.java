package dev.fizlrock.todo.domain.exception;

import java.util.UUID;

/** ProjectNotFoundException */
public class ProjectNotFoundException extends EntityNotFoundException {

  public ProjectNotFoundException(UUID projectId) {
    super(projectId);
    // super();
  }

  @Override
  public String getMessage() {
    return "Проект с идентификатором %s не найден".formatted(id);
  }
}

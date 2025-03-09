package dev.fizlrock.todo.domain.exception;

import java.util.UUID;

/** ProjectNotFoundException */
public class TaskNotFoundException extends EntityNotFoundException {

  public TaskNotFoundException(UUID taskId) {
    super(taskId);
  }

  @Override
  public String getMessage() {
    return "Задача с идентификатором %s не найдена".formatted(id);
  }
}

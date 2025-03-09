package dev.fizlrock.todo.domain.exception;

public abstract class EntityNotFoundException extends TodoAppException {

  protected final Object id;

  public EntityNotFoundException(Object id) {
    this.id = id;
  }

  @Override
  public String getMessage() {
    return "Сущность с идентификатором %s не найдена".formatted(id);
  }
}

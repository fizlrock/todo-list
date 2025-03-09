package dev.fizlrock.todo.domain.exception;

/** IllegalProjectDescriptionException */
public class IllegalProjectDescriptionException extends TodoAppException {
  public final String description, reason;

  public IllegalProjectDescriptionException(String description, String reason) {
    this.description = description;
    this.reason = reason;
  }

  @Override
  public String getMessage() {
    return "Недопустимое описание проекта: <%s>. %s".formatted(description, reason);
  }
}

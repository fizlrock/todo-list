package dev.fizlrock.todo.domain.exception;

/** IllegalTaskNameException */
public abstract class IllegalNameException extends TodoAppException {

  public final String name, reason;

  public IllegalNameException(String name, String reason) {
    this.name = name;
    this.reason = reason;
  }

  @Override
  public String getMessage() {
    return reason;
  }
}

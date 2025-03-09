package dev.fizlrock.todo.domain.exception;

/** TimeConflictException */
public class TimeConflictException extends TodoAppException {
  public final String reason;

  public TimeConflictException(String reason) {
    this.reason = reason;
  }

  @Override
  public String getMessage() {
    return reason;
  }
}

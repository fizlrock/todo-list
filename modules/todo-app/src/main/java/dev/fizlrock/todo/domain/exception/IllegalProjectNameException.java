package dev.fizlrock.todo.domain.exception;

/** IllegalProjectNameException */
public class IllegalProjectNameException extends IllegalNameException {

  public IllegalProjectNameException(String name, String descr) {
    super(name, descr);
  }
}

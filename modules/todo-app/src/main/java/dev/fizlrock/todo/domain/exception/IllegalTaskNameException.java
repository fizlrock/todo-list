package dev.fizlrock.todo.domain.exception;

/** IllegalTaskNameException */
public class IllegalTaskNameException extends IllegalNameException {

  public IllegalTaskNameException(String name, String descr) {
    super(name, descr);
  }
}

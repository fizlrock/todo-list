package dev.fizlrock.todo.domain.exception;

/** TaskNameDublicateException */
public class TaskNameDublicateException extends IllegalNameException {

  public TaskNameDublicateException(String name) {
    super(name, "Уже существует задача с таким именем в проекте.");
  }
}

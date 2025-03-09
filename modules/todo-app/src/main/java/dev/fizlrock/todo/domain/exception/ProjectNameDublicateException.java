package dev.fizlrock.todo.domain.exception;

/** ProjectNameDublicateException */
public class ProjectNameDublicateException extends IllegalNameException {

  public ProjectNameDublicateException(String projectName) {
    super(projectName, "Уже существует проект с таким именем");
  }
}

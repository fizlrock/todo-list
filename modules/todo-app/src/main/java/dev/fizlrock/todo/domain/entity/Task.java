package dev.fizlrock.todo.domain.entity;

import dev.fizlrock.todo.domain.exception.IllegalTaskNameException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;

/** Task */
@Getter
@EqualsAndHashCode
@ToString
public class Task {

  @PersistenceCreator
  public static Task loadFromDatabase(
      UUID id, UUID projectId, String name, LocalDate finishDate, boolean completed) {

    Task task = new Task(id);
    task.setProjectId(projectId);
    task.setName(name);
    task.setFinishDate(finishDate);
    task.setCompleted(completed);
    return task;
  }

  protected static Task createNew(UUID projectId, String name, LocalDate plannedFinish) {
    var t = new Task();
    t.setProjectId(projectId);
    t.setName(name);
    t.setFinishDate(plannedFinish);

    return t;
  }

  private UUID id;
  private UUID projectId;

  private String name;

  private boolean completed = false;
  private LocalDate finishDate;

  protected Task() {
    this.id = UUID.randomUUID();
  }

  private Task(UUID id) {
    this.id = id;
  }

  public void makeCompleted() {
    completed = true;
  }

  public void makeUncompleted() {
    completed = false;
  }

  public void setCompleted(boolean isCompleted) {
    this.completed = isCompleted;
  }

  public boolean isCompleted() {
    return completed;
  }

  public boolean isUncompleted() {
    return !completed;
  }

  public void setName(String name) {

    Objects.requireNonNull(name, "Имя задачи не может быть null");
    name = name.strip();
    if (name.isBlank()) throw new IllegalTaskNameException(name, "Имя задачи не может быть пустым");

    if (name.length() > 20)
      throw new IllegalTaskNameException(name, "Длина имени не может быть больше 20 символов");
    this.name = name;
  }

  public void setFinishDate(LocalDate finishDate) {
    this.finishDate = finishDate;
  }

  private void setProjectId(UUID projectId) {
    Objects.requireNonNull(projectId, "ID проекта не может быть null");
    this.projectId = projectId;
  }
}

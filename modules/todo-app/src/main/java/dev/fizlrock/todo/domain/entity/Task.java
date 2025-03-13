package dev.fizlrock.todo.domain.entity;

import dev.fizlrock.todo.domain.exception.IllegalTaskNameException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

/** Task */
@Getter
@ToString
public class Task extends BaseEntity<UUID> {

  public static Task loadFromDatabase(
      UUID taskId, UUID projectId, String name, LocalDate plannedFinish) {

    Task task = new Task(taskId);
    task.setProjectId(projectId);
    task.setName(name);
    task.setFinishDate(plannedFinish);
    return task;
  }

  protected static Task createNew(UUID projectId, String name, LocalDate plannedFinish) {
    var t = new Task();
    t.setProjectId(projectId);
    t.setName(name);
    t.setFinishDate(plannedFinish);

    return t;
  }

  private UUID projectId;

  private String name;

  private boolean isCompleted = false;
  private LocalDate finishDate;

  protected Task() {
    super(UUID.randomUUID(), true);
  }

  private Task(UUID id) {
    super(id, false);
  }

  public void makeCompleted() {
    isCompleted = true;
  }

  public void makeUncompleted() {
    isCompleted = false;
  }

  public void setCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  public boolean isCompleted() {
    return isCompleted;
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

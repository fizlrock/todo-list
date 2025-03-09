package dev.fizlrock.todo.domain.entity;

import dev.fizlrock.todo.domain.exception.IllegalTaskNameException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

/** Task */
@Entity
@Getter
@ToString
public class Task {

  protected static Task createNew(UUID projectId, String name, LocalDate plannedFinish) {
    var t = new Task();
    t.setId(UUID.randomUUID());
    t.setProjectId(projectId);
    t.setName(name);
    t.setFinishDate(plannedFinish);

    return t;
  }

  @Id private UUID id;

  private UUID projectId;

  private String name;

  @Column(name = "completed")
  private boolean isCompleted = false;

  @Column(name = "planned_end_date")
  private LocalDate finishDate;

  protected Task() {}

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

  private void setId(UUID id) {
    Objects.requireNonNull(id, "ID задачи не может быть null");
    this.id = id;
  }

  private void setProjectId(UUID projectId) {
    Objects.requireNonNull(projectId, "ID проекта не может быть null");
    this.projectId = projectId;
  }
}

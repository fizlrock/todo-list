package dev.fizlrock.todo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

/** Task */
@Entity
@Getter
@ToString
public class Task {

  protected Task() {}

  protected static Task createNew(UUID projectId, String name, LocalDate plannedFinish) {
    var t = new Task();

    t.id = UUID.randomUUID();
    t.projectId = projectId;
    t.setName(name);
    t.setFinishDate(plannedFinish);

    return t;
  }

  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID projectId;

  private String name;

  @Column(name = "completed")
  private boolean isCompleted = false;

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
    if (name.isBlank()) throw new IllegalArgumentException("Blank task name not permited");
    this.name = name;
  }

  public void setFinishDate(LocalDate finishDate) {
    this.finishDate = finishDate;
  }

  @Column(name = "planned_end_date")
  private LocalDate finishDate;
}

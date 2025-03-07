package dev.fizlrock.todo.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/** Project */
@Entity
@ToString
@Slf4j
public class Project {

  public static Project createNewProject(
      final String name, final String description, final LocalDate start, final LocalDate end) {

    final var project = new Project();

    project.setId(UUID.randomUUID());
    project.setName(name);
    project.setDescription(description);
    project.setDates(start, end);

    return project;
  }

  @OneToMany(
      mappedBy = "projectId",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true) // Bidirectional:  Customer owns the relationship
  @MapKey(name = "id") // Используем id задачи в качестве ключа
  Map<UUID, Task> tasks = new HashMap<>();

  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name, description;

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  @Column(name = "start_time")
  private LocalDate startDate;

  @Column(name = "end_time")
  private LocalDate endDate;

  private Project() {}

  public Task addTask(final String taskName, final LocalDate plannedEndDate) {
    final var t = Task.createNew(this.id, taskName, plannedEndDate);
    validateTaskNewName(taskName);
    validateTaskEndDate(plannedEndDate);
    tasks.put(t.getId(), t);
    return t;
  }

  public Task removeTask(final UUID taskId) {

    final var t = tasks.remove(taskId);
    if (t == null) throw new IllegalArgumentException("task id not found");
    log.info("Удаление задачи: {}", t);
    return t;
  }

  public Task updateTask(
      final UUID taskId,
      final String newTaskName,
      final LocalDate newEndDate,
      final boolean state) {

    final var task = tasks.get(taskId);
    if (task == null) throw new IllegalArgumentException("task id not found");

    if (!task.getName().equals(newTaskName)) validateTaskNewName(newTaskName);

    if (!task.getFinishDate().equals(newEndDate)) validateTaskEndDate(newEndDate);

    task.setFinishDate(newEndDate);
    task.setName(newTaskName);
    task.setCompleted(state);
    return null;
  }

  public Collection<Task> getTasks() {
    return Collections.unmodifiableCollection(tasks.values());
  }

  public void setName(final String name) {
    if (name.equals(this.name)) return;
    if (name.isBlank()) throw new IllegalArgumentException("blank name not permited");
    if (name.strip().length() > 30) throw new IllegalArgumentException("to long name");

    this.name = name.strip();
  }

  public void setDescription(final String description) {
    if (description.equals(this.description))
      if (name.strip().length() > 300) throw new IllegalArgumentException("to long description");
    this.description = description.strip();
  }

  public void setDates(final LocalDate startDate, final LocalDate endDate) {
    if (startDate.isAfter(endDate))
      throw new IllegalArgumentException("Date conflict. Project start after end");

    final var confilictTask =
        tasks.values().stream()
            .filter(
                t -> t.getFinishDate().isBefore(startDate) || t.getFinishDate().isAfter(endDate))
            .findAny();

    if (confilictTask.isPresent()) throw new IllegalArgumentException("Task finish time conflict");

    this.startDate = startDate;
    this.endDate = endDate;
  }

  private void validateTaskEndDate(final LocalDate date) {
    if (date.isBefore(this.startDate) || date.isAfter(this.endDate))
      throw new IllegalArgumentException("Time conflict");
  }

  private void validateTaskNewName(final String name) {
    final var taskWithSameName =
        tasks.values().stream().filter(t -> t.getName().equals(name)).findAny();
    if (taskWithSameName.isPresent()) throw new IllegalArgumentException("task name dublicate");
  }

  private void setId(final UUID id) {
    this.id = id;
  }
}

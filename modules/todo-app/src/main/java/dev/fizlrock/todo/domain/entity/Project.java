package dev.fizlrock.todo.domain.entity;

import static java.util.Objects.requireNonNull;

import dev.fizlrock.todo.domain.exception.IllegalProjectDescriptionException;
import dev.fizlrock.todo.domain.exception.IllegalProjectNameException;
import dev.fizlrock.todo.domain.exception.TaskNameDublicateException;
import dev.fizlrock.todo.domain.exception.TaskNotFoundException;
import dev.fizlrock.todo.domain.exception.TimeConflictException;
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

  public Task removeTaskById(final UUID taskId) {

    final var t = tasks.remove(taskId);
    if (t == null) throw new TaskNotFoundException(taskId);
    log.debug("Удаление задачи: {}", t);
    return t;
  }

  public Task getTaskById(UUID taskId) {

    final var t = tasks.get(taskId);
    if (t == null) throw new TaskNotFoundException(taskId);
    return t;
  }

  public Task updateTask(
      final UUID taskId,
      final String newTaskName,
      final LocalDate newEndDate,
      final boolean state) {

    final var task = tasks.get(taskId);

    if (task == null) throw new TaskNotFoundException(taskId);

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
    requireNonNull(name);

    if (name.equals(this.name)) return;
    if (name.isBlank())
      throw new IllegalProjectNameException(name, "Пустое имя проекта не допустимо");
    if (name.strip().length() > 30)
      throw new IllegalProjectNameException(name, "Имя проекта не может быть длинне 30 символов");

    this.name = name.strip();
  }

  public void setDescription(final String description) {
    requireNonNull(description);

    if (description.equals(this.description)) return;

    if (name.strip().length() > 100)
      throw new IllegalProjectDescriptionException(
          description, "Описание проекта может быть не более 100 символов");
    this.description = description.strip();
  }

  public void setDates(final LocalDate startDate, final LocalDate endDate) {
    if (startDate.isAfter(endDate))
      throw new TimeConflictException("Date conflict. Project start after end");

    final var confilictTask =
        tasks.values().stream()
            .filter(
                t -> t.getFinishDate().isBefore(startDate) || t.getFinishDate().isAfter(endDate))
            .findAny();

    if (confilictTask.isPresent())
      throw new TimeConflictException(
          "Существует задача, чей дедлайн не входит в новые сроки проведения проекта");

    this.startDate = startDate;
    this.endDate = endDate;
  }

  private void validateTaskEndDate(final LocalDate date) {
    if (date.isBefore(this.startDate) || date.isAfter(this.endDate))
      throw new TimeConflictException("Дедлайн задачи не входит в сроки проведения проекта");
  }

  private void validateTaskNewName(final String name) {
    final var taskWithSameName =
        tasks.values().stream().filter(t -> t.getName().equals(name)).findAny();
    if (taskWithSameName.isPresent()) throw new TaskNameDublicateException(name);
  }

  private void setId(final UUID id) {
    requireNonNull(id);
    this.id = id;
  }
}

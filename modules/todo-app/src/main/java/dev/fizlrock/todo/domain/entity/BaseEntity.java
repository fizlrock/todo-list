package dev.fizlrock.todo.domain.entity;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

/** BaseEntity */
@EqualsAndHashCode
abstract class BaseEntity<T> implements Persistable<T> {

  public BaseEntity(T id, boolean isNew) {
    this.isNew = isNew;
    requireNonNull(id);
    this.id = id;
  }

  private T id;
  private boolean isNew;

  public T getId() {
    return id;
  }

  @Override
  public boolean isNew() {
    return isNew;
  }
}

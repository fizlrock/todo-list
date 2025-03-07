package dev.fizlrock.todo.domain.ports;

/** CrudRepository */
public interface CrudRepository<T> {
  void save(T entity);

  void delete(T entity);
}

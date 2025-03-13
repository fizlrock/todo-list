package dev.fizlrock.todo.repository.jpa;

import dev.fizlrock.todo.domain.entity.Project;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

/** ProjectSpecification */
public class ProjectSpecification {

  public static Specification<Project> hasNameLike(String name) {
    return (root, query, criteriaBuilder) ->
        name == null ? null : criteriaBuilder.like(root.get("name"), name);
  }

  // Фильтрация проектов, начинающихся после указанной даты
  public static Specification<Project> startAfter(LocalDate date) {
    return (root, query, criteriaBuilder) -> {
      if (date == null) {
        return null; // Возвращаем null, если дата не указана, чтобы не влиять на другие условия
      }
      return criteriaBuilder.greaterThan(root.get("startDate"), date);
    };
  }

  // Фильтрация проектов, заканчивающихся до указанной даты
  public static Specification<Project> endBefore(LocalDate date) {
    return (root, query, criteriaBuilder) -> {
      if (date == null) {
        return null; // Возвращаем null, если дата не указана
      }
      return criteriaBuilder.lessThan(root.get("endDate"), date);
    };
  }
}

package dev.fizlrock.todo.repository.relational.repository;

import dev.fizlrock.todo.repository.relational.entity.ProjectAgregate;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAgregateRepository
    extends PagingAndSortingRepository<ProjectAgregate, UUID>,
        CrudRepository<ProjectAgregate, UUID> {}

package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;
import java.util.Optional;

public record ProjectFilterRq(
    Long skip,
    Long limit,
    Optional<String> name,
    Optional<LocalDate> start,
    Optional<LocalDate> end) {}

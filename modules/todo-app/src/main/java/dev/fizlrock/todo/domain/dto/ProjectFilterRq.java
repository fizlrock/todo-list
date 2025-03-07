package dev.fizlrock.todo.domain.dto;

import java.time.LocalDate;

public record ProjectFilterRq(Long skip, Long limit, String name, LocalDate start, LocalDate end) {}

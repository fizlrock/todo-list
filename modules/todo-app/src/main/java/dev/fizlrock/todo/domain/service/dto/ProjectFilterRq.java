package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;

public record ProjectFilterRq(Long skip, Long limit, String name, LocalDate start, LocalDate end) {}

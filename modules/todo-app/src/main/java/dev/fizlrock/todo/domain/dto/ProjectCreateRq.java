package dev.fizlrock.todo.domain.dto;

import java.time.LocalDate;

public record ProjectCreateRq(
    String name, String description, LocalDate startTime, LocalDate endTime) {}

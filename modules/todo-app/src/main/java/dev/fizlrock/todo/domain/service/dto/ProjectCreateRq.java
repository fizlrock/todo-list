package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;

public record ProjectCreateRq(
    String name, String description, LocalDate startTime, LocalDate endTime) {}

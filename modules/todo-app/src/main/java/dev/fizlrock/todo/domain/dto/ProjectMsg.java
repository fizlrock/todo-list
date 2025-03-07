package dev.fizlrock.todo.domain.dto;

import java.time.LocalDate;

public record ProjectMsg(
    String id, String name, String description, LocalDate startDate, LocalDate endDate) {}

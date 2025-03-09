package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;

public record ProjectMsg(
    String id, String name, String description, LocalDate startDate, LocalDate endDate) {}

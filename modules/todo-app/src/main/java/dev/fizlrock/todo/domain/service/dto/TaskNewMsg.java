package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;

/** TaskNewMsg */
public record TaskNewMsg(String name, String description, boolean completed, LocalDate date) {}

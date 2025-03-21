package dev.fizlrock.todo.domain.service.dto;

import java.time.LocalDate;

public record TaskMsg(String id, String name, boolean completed, LocalDate date) {}

package dev.fizlrock.todo.domain.service.dto;

/** TaskCreateRq */
public record TaskCreateRq(String projectId, TaskNewMsg task) {}

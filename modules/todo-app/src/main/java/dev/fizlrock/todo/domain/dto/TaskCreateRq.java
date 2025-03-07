package dev.fizlrock.todo.domain.dto;

/** TaskCreateRq */
public record TaskCreateRq(String projectId, TaskNewMsg task) {}

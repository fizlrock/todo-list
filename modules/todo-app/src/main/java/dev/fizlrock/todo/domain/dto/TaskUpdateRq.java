package dev.fizlrock.todo.domain.dto;

public record TaskUpdateRq(String projectId, String taskId, TaskNewMsg task) {}

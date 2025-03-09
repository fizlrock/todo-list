package dev.fizlrock.todo.domain.service.dto;

public record TaskUpdateRq(String projectId, String taskId, TaskNewMsg task) {}

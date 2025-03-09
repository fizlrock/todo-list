package dev.fizlrock.todo.restapi.advice;

import dev.fizlrock.todo.domain.exception.EntityNotFoundException;
import dev.fizlrock.todo.domain.exception.TodoAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/** ControllerExceptionAdvice */
@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

  @ExceptionHandler(TodoAppException.class)
  ResponseStatusException handleDomainException(TodoAppException e) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  ResponseStatusException handleDomainNotFoundException(EntityNotFoundException e) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  ResponseStatusException handleOtherException(Exception e) {
    log.error("Ошибка: {}", e);
    return new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, "Внутрення ошибка сервера, разработчик оповещен");
  }
}

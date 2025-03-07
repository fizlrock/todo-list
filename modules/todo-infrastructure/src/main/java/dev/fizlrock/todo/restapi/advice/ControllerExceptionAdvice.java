package dev.fizlrock.todo.restapi.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/** ControllerExceptionAdvice */
@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

  @ExceptionHandler(Exception.class)
  ResponseStatusException handle(Exception e) {
    log.error("Ошибка в контроллере", e);
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
  }
}

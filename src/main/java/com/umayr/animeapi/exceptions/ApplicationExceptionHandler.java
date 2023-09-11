package com.umayr.animeapi.exceptions;

import com.umayr.animeapi.model.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<ExceptionResponse> handleWebExchangeException(WebExchangeBindException e) {
    List<FieldError> fieldErrors = e.getFieldErrors();

    HashMap<String, String> errors = new HashMap<>();
    fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

    String mapKeyValuesAsString = errors.keySet()
            .stream().map(key -> key + "=" + errors.get(key))
            .collect(Collectors.joining(", ", "{", "}"));

    log.error("Error(s) occurred during request to an endpoint: {}", kv("Errors", mapKeyValuesAsString));
    return ResponseEntity.badRequest().body(
            ExceptionResponse.builder()
                    .message(mapKeyValuesAsString)
                    .status(400)
                    .build()
    );
  }
}

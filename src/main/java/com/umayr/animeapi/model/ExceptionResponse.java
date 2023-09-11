package com.umayr.animeapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class ExceptionResponse {
  private String message;
  private int status;
}

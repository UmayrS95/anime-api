package com.umayr.animeapi.acceptancetests.helpers;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Objects;

public class GenReqSpec {

  private static final String LOCALHOST = "http://localhost";

  private static final String TEST_ENVIRONMENT = Objects.requireNonNull(System.getProperty("test_env"),
          "Please pass a system variable in format test_env=local");

  public static RequestSpecification ReqSpec(String path) {
    int basePort = 8059;
    if (TEST_ENVIRONMENT.equals("local")) {
      return reqSpecLocal(LOCALHOST + path, basePort);
    } else {
      throw new IllegalArgumentException("Invalid Env");
    }
  }

  public static RequestSpecification reqSpecLocal(String baseUriPath, int basePort) {
    return RestAssured.given()
            .urlEncodingEnabled(false)
            .when()
            .baseUri(baseUriPath)
            .port(basePort);
  }
}

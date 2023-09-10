package com.umayr.animeapi.acceptancetests;

import com.umayr.animeapi.acceptancetests.helpers.GenReqSpec;
import com.umayr.animeapi.model.Anime;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class V1GetAnimeIT {

  private static RequestSpecification requestSpecification;

  @BeforeEach
  public void BeforeEachTest() {
    String path = "/v1/getAnime";

    requestSpecification = GenReqSpec.ReqSpec(path);
  }

  @Test
  @DisplayName("GET anime endpoint returns list of current anime in db")
  void GETAnimeReturns200WithResponseBody() {
    Anime[] animeList = requestSpecification
            .contentType(ContentType.JSON)
            .get()
            .then()
            .log().ifError()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .as(Anime[].class);

    Assertions.assertEquals(animeList[0].getTitle(), "One Piece");
  }
}

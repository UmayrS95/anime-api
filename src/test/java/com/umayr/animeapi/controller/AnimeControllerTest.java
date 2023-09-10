package com.umayr.animeapi.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.umayr.animeapi.repository.AnimeRepository;
import com.umayr.animeapi.service.AnimeService;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.Stream;

import static com.umayr.animeapi.LogUtils.logger;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AnimeController.class)
public class AnimeControllerTest {
  @MockBean
  private AnimeService animeService;
  @MockBean
  private AnimeRepository animeRepository;
  @Autowired
  private WebTestClient webTestClient;
  private ListAppender<ILoggingEvent> loggingEventListAppender;

  @BeforeEach
  public void beforeEachTest(){
    loggingEventListAppender = logger();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getMethods")
  void testGetEndpointMethod(String testName, String param, int status, Level logType, String logMessage) {
    webTestClient
            .get()
            .uri("/v1/getAnime" + param)
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus()
            .isEqualTo(status);

    await().untilAsserted(() -> assertThat(loggingEventListAppender.list)
            .extracting(ILoggingEvent::getLevel, ILoggingEvent::getMessage)
            .containsAnyOf(Tuple.tuple(logType, logMessage)));
  }

  public static Stream<Arguments> getMethods() {
    return Stream.of(
            Arguments.of("GET returns 200 with correct logging event", "", 200, Level.INFO, "GET all anime endpoint called"),
            Arguments.of("GET with param returns 200 with correct logging event", "?title=samurai", 200, Level.INFO, "GET all anime endpoint called with title: {}")
    );
  }
}

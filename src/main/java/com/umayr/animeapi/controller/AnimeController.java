package com.umayr.animeapi.controller;

import com.umayr.animeapi.model.Anime;
import com.umayr.animeapi.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1")
public class AnimeController {
  @Autowired
  private AnimeService animeService;

  @GetMapping("/getAnime")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Anime> getAllAnime(@RequestParam(required = false) String title) {
    if (title == null) {
      log.info("GET all anime endpoint called");
      return animeService.findAll();
    } else {
      log.info("GET all anime endpoint called with title: {}", kv("title", title));
      return animeService.findByTitleContaining(title);
    }
  }

  @PostMapping("/saveAnime")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Anime> createAnime(@RequestBody Anime anime) {
    return animeService.save(anime);
  }

  @PutMapping("/updateAnime/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Anime> updateAnimeById(@PathVariable("id") String id, Anime anime) {
    return animeService.update(id, anime);
  }
}

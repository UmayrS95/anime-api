package com.umayr.animeapi.service;

import com.umayr.animeapi.model.Anime;
import com.umayr.animeapi.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AnimeService {
  @Autowired
  private AnimeRepository animeRepository;

  public Flux<Anime> findAll() {
    return animeRepository.findAll();
  }

  public Flux<Anime> findByTitleContaining(String title) {
    return animeRepository.findByTitleContaining(title);
  }

  public Mono<Anime> findById(String id) {
    return animeRepository.findById(id);
  }

  public Mono<Anime> save(Anime anime) {
    Anime animeToSave = Anime.builder()
            .title(anime.getTitle())
            .releaseYear(anime.getReleaseYear())
            .build();

    return animeRepository.save(animeToSave);
  }

  public Mono<Anime> update(String id, Anime anime) {
    return animeRepository.findById(id)
            .map(Optional::of)
            .defaultIfEmpty(Optional.empty())
            .flatMap(optionalAnime -> {
              if (optionalAnime.isPresent()) {
                anime.setId(id);
                return animeRepository.save(anime);
              }
              return Mono.empty();
            });
  }

  public Mono<Void> deleteById(String id) {
    return animeRepository.deleteById(id);
  }

//  public Mono<Void> deleteAll() {
//    return animeRepository.deleteAll();
//  }
}

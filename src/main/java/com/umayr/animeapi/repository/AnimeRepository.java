package com.umayr.animeapi.repository;

import com.umayr.animeapi.model.Anime;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AnimeRepository extends ReactiveMongoRepository<Anime, String> {
  Flux<Anime> findByTitleContaining(String title);
}

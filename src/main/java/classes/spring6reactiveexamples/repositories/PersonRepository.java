package classes.spring6reactiveexamples.repositories;

import classes.spring6reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();
}

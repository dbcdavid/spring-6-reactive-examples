package classes.spring6reactiveexamples.repositories;

import classes.spring6reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person john = Person.builder().id(1).firstName("John").lastName("Snow").build();
    Person tyrion = Person.builder().id(2).firstName("Tyrion").lastName("Lannister").build();
    Person daenerys = Person.builder().id(3).firstName("Daenerys").lastName("Targarien").build();
    Person arya = Person.builder().id(4).firstName("Arya").lastName("Stark").build();
    Person sansa = Person.builder().id(5).firstName("Sansa").lastName("Stark").build();

    @Override
    public Mono<Person> getById(final Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(john, tyrion, daenerys, arya, sansa);
    }
}

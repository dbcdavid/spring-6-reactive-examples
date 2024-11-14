package classes.spring6reactiveexamples.repositories;

import classes.spring6reactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {
    PersonRepository repo = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock(){
        Mono<Person> personMono = repo.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testMonoByIdSubscriber(){
        Mono<Person> personMono = repo.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapMonoOperation(){
        Mono<Person> personMono = repo.getById(1);

        personMono.map(Person::getFirstName).subscribe(firstName -> {
            System.out.println(firstName);
        });
    }

    @Test
    void testFluxBlockFirst(){
        Flux<Person> personFlux = repo.findAll();

        Person person = personFlux.blockFirst();
        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber(){
        Flux<Person> personFlux = repo.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapFluxOperation(){
        Flux<Person> personFlux = repo.findAll();

        personFlux.map(Person::getFirstName).subscribe(firstName -> {
            System.out.println(firstName);
        });
    }

    @Test
    void testFluxToList(){
        Flux<Person> personFlux = repo.findAll();
        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.toString()));
        });
    }

    @Test
    void testFilterOnName(){
        repo.findAll()
                .filter(person -> person.getLastName().equals("Stark"))
                .subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testGetByIdFound(){
        Mono<Person> personMono = repo.getById(1);

        assertTrue(personMono.hasElement().block());
    }

    @Test
    void testGetByIdFoundStepVerifier(){
        Mono<Person> personMono = repo.getById(1);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.getFirstName());
        });
    }

    @Test
    void testGetByIdNotFound(){
        Mono<Person> personMono = repo.getById(-1);

        assertFalse(personMono.hasElement().block());
    }

    @Test
    void testGetByIdNotFoundStepVerifier(){
        Mono<Person> personMono = repo.getById(-1);

        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.getFirstName());
        });
    }

    @Test
    void testFindPersonByIdNotFound(){
        Flux<Person> personFlux = repo.findAll();
        final Integer id = 8;

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single();
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println(throwable.getMessage());
        });
    }
}
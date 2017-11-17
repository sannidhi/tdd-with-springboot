package com.example.tddwebfluxsample;

import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.example.tddwebfluxsample.CarFactory.PRIUS;
import static com.example.tddwebfluxsample.CarFactory.listOfCars;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebFluxTest
public class CarControllerTests {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void cars_returnsAllCars() throws Exception {
        given(carRepository.findAll()).willReturn(Flux.fromIterable(listOfCars()));

        webTestClient.get().uri("/cars")
                .exchange().expectStatus().isOk()
                .expectBody().jsonPath("$.[0].name").isEqualTo("model-x")
                .jsonPath("$.[0].description").isEqualTo("full-sized, all-electric");
        verify(carRepository).findAll();
    }

    @Test
    public void getCar_returnsCarWithNameAndDescription() throws Exception {
		given(carRepository.findByName("prius")).willReturn(Mono.just(PRIUS));

		webTestClient.get().uri("/cars/{name}", "prius")
				.exchange().expectStatus().isOk()
				.expectBody().jsonPath("name").isEqualTo(PRIUS.getName())
				.jsonPath("description").isEqualTo(PRIUS.getDescription());
		verify(carRepository).findByName(PRIUS.getName());
    }
}

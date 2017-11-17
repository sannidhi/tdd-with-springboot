package com.example.tddwebfluxsample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.example.tddwebfluxsample.CarFactory.MODEL_X;
import static com.example.tddwebfluxsample.CarFactory.PRIUS;
import static com.example.tddwebfluxsample.CarFactory.listOfCars;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TddWebfluxSampleApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ReactiveMongoOperations operations;

	@Before
	public void setUp() throws Exception {
		this.operations
				.createCollection(Car.class, CollectionOptions.empty().size(1024 * 1024).maxDocuments( 100).capped())
				.then()
				.block();

		this.carRepository
				.saveAll(listOfCars())
				.then()
				.block();
	}

	@Test
	public void getAllCars() {
		this.webTestClient.get().uri("/cars")
				.exchange().expectStatus().isOk()
				.expectBodyList(Car.class)
				.contains(PRIUS, MODEL_X);
	}

}

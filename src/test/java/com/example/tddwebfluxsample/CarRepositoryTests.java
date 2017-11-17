package com.example.tddwebfluxsample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.tddwebfluxsample.CarFactory.MODEL_X;
import static com.example.tddwebfluxsample.CarFactory.PRIUS;
import static com.example.tddwebfluxsample.CarFactory.listOfCars;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CarRepositoryTests {

	@Autowired
    private CarRepository carRepository;

    @Before
    public void setUp() throws Exception {
        this.carRepository.saveAll(listOfCars())
			.then()
			.block();
    }

    @After
    public void tearDown() throws Exception {
        carRepository.deleteAll()
			.then()
			.block();
    }

    @Test
    public void findAll_returnsAllCarsInRepository() throws Exception {
        StepVerifier.create(carRepository.findAll())
				.expectNextSequence(listOfCars())
				.verifyComplete();
    }

    @Test
    public void findByName_returnsCarDescription() {
		StepVerifier.create(carRepository.findByName(PRIUS.getName()))
				.expectNext(PRIUS)
				.verifyComplete();
    }

}
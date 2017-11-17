package com.example.tddwebfluxsample;

import java.util.List;

import org.assertj.core.util.Lists;

class CarFactory {
    static final Car MODEL_X = new Car(1, "model-x", "full-sized, all-electric");
    static final Car PRIUS = new Car(2, "prius", "hybrid car");


    static List<Car> listOfCars() {
        return Lists.newArrayList(MODEL_X, PRIUS);
    }
}

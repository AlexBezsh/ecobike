package com.qualityunit.ecobike.repository;

import com.qualityunit.ecobike.model.Bike;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BikeRepository {

    private static BikeRepository repository;
    private static AtomicBoolean hasChanged = new AtomicBoolean(false);
    private List<Bike> bikes;

    private BikeRepository() {
    }

    public static BikeRepository getInstance() {
        BikeRepository result = repository;
        if (result != null) {
            return result;
        }
        synchronized (BikeRepository.class) {
            if (repository == null) {
                repository = new BikeRepository();
            }
            return repository;
        }
    }

    public synchronized void initDatabase(List<Bike> bikes) {
        if (this.bikes == null) {
            this.bikes = bikes;
        }
    }

    public List<Bike> findAll() {
        return Collections.unmodifiableList(bikes);
    }

    public synchronized void saveNewBike(Bike bike) {
        bikes.add(bike);
        hasChanged.set(true);
    }

    public synchronized static boolean hasChanged() {
        return hasChanged.get();
    }

    public synchronized static void dataSaved() {
        hasChanged.set(false);
    }
}

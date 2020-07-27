package com.qualityunit.ecobike.repository;

import com.qualityunit.ecobike.model.AbstractBike;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BikeRepository {

    private static BikeRepository repository;
    private static AtomicBoolean hasChanged = new AtomicBoolean(false);
    private List<AbstractBike> bikes;

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

    public synchronized void initDatabase(List<AbstractBike> bikes) {
        if (this.bikes == null) {
            this.bikes = bikes;
        }
    }

    public List<AbstractBike> findAll() {
        return Collections.unmodifiableList(bikes);
    }

    public synchronized void saveNewBike(AbstractBike bike) {
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

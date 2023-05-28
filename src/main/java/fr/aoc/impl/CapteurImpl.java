package fr.aoc.impl;

import fr.aoc.Capteur;
import fr.aoc.ObserverDeCapteurAsync;
import fr.aoc.strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CapteurImpl implements Capteur {

    private ArrayList<Integer> values;
    private int value;
    private AlgoDiffusion algo;
    private boolean locked;
    private List<ObserverDeCapteurAsync> observers;

    public CapteurImpl(AlgoDiffusion algo) {
        this.values = new ArrayList<>();
        this.value = 0;
        this.algo = algo;
        this.locked = false;

        algo.configure(this);
        observers = new ArrayList<>();
    }

    @Override
    public void attach(ObserverDeCapteurAsync observer) {
        this.observers.add(observer);
    }

    @Override
    public void detach(ObserverDeCapteurAsync observer) {
        this.observers.remove(observer);
    }

    @Override
    public Integer getValue(ObserverDeCapteurAsync observerDeCapteur) {
        algo.valueRead(observerDeCapteur);
        return this.algo.getCurrentValue();
    }

    @Override
    public void lock() {
        locked = true;
    }

    @Override
    public void unlock() {
        locked = false;
    }

    @Override
    public boolean isLock() {
        return this.locked;
    }

    @Override
    public void tick() {
        algo.execute();
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void incValue() {
        ++this.value;
        values.add(value);
    }

    @Override
    public ArrayList<Integer> getValues() {
        return values;
    }

    @Override
    public void notifyObservers() {
        for (ObserverDeCapteurAsync observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public List<ObserverDeCapteurAsync> getObservers() {
        return Collections.unmodifiableList(observers);
    }
}
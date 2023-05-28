package fr.aoc.strategy.impl;

import fr.aoc.Capteur;
import fr.aoc.ObserverDeCapteurAsync;
import fr.aoc.strategy.AlgoDiffusion;

public class DiffusionSequentielle implements AlgoDiffusion {

    private Capteur capteur;
    private int nbObserversNotUpdated;
    private int currentValue;

    @Override
    public void configure(Capteur capteur) {
        this.capteur = capteur;
    }

    @Override
    public void execute() {
        capteur.incValue();

        if (capteur.isLock()) return;
        capteur.lock();
        currentValue = capteur.getValue();

        nbObserversNotUpdated = capteur.getObservers().size();
        capteur.notifyObservers();
    }

    @Override
    public void valueRead(ObserverDeCapteurAsync observer) {
        nbObserversNotUpdated--;
        if (nbObserversNotUpdated <= 0)
            capteur.unlock();
    }

    @Override
    public int getCurrentValue() {
        return currentValue;
    }
}
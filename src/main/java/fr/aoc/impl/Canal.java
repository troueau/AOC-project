package fr.aoc.impl;

import fr.aoc.Capteur;
import fr.aoc.CapteurAsync;
import fr.aoc.ObserverDeCapteur;
import fr.aoc.ObserverDeCapteurAsync;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Canal implements ObserverDeCapteurAsync, CapteurAsync {

    private ScheduledExecutorService scheduledExecutorService;
    private static final int SIZE = 5;
    private Capteur capteur;
    private ObserverDeCapteur observerDeCapteur;
    private static final int MAX_DELAY = 1500;
    private static final int MIN_DELAY = 500;

    public Canal(Capteur capteur, ObserverDeCapteur observerDeCapteur, ScheduledExecutorService scheduledExecutorService) {
        this.observerDeCapteur = observerDeCapteur;
        this.capteur = capteur;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public ScheduledFuture<Integer> getValue() {
        return scheduledExecutorService.schedule(() -> {
            return capteur.getValue(this);
        }, getRandomDelay(), java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public ScheduledFuture update(Capteur capteur) {
        return scheduledExecutorService.schedule(() -> observerDeCapteur.update(this), getRandomDelay(), TimeUnit.MILLISECONDS);
    }


    private int getRandomDelay() {
        return (int) (Math.random() * ((MAX_DELAY - MIN_DELAY) + 1)) + MIN_DELAY;
    }
}

package fr.aoc;

import java.util.concurrent.Future;

public interface ObserverDeCapteurAsync {

    /**
     * Met à jour l'observer
     * @param subject le capteur qui a changé
     */
    public Future update(Capteur subject);

}
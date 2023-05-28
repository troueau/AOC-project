package fr.aoc.strategy.impl;

import fr.aoc.Capteur;
import fr.aoc.ObserverDeCapteurAsync;
import fr.aoc.strategy.AlgoDiffusion;

public class DiffusionEpoque implements AlgoDiffusion {

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
                currentValue = capteur.getValue();

                if (capteur.isLock()) return;
                capteur.lock();

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

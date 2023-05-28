package fr.aoc.strategy;

import fr.aoc.Capteur;
import fr.aoc.ObserverDeCapteurAsync;

public interface AlgoDiffusion {

        /**
         * Configure l'algorithme de diffusion.
         * @param capteur le capteur à configurer
         */
        void configure(Capteur capteur);

        /**
         * Execute l'algorithme de diffusion.
         */
        void execute();

        /**
         * Notifie l'algorithme que la valeur a été lue par un observateur.
         * @param observer
         */
        void valueRead(ObserverDeCapteurAsync observer);

        int getCurrentValue();
}

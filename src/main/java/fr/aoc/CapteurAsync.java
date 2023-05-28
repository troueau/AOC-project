package fr.aoc;

import java.util.concurrent.Future;

public interface CapteurAsync {

    /**
     * @return la valeur courante du capteur
     */
    Future<Integer> getValue();
    
}
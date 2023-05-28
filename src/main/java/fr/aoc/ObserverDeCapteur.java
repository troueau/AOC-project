package fr.aoc;

import java.util.ArrayList;

public interface ObserverDeCapteur {

    /**
     * Met à jour l'observer
     * @param capteurAsync le capteur qui a changé
     */
    public void update(CapteurAsync capteurAsync);

    /**
     * Retourne la liste des valeurs affichées
     * @return la liste des valeurs affichées
     */
    public ArrayList<Integer> getValues();

    /**
     * Retourne le nom de l'observer
     * @return le nom de l'observer
     */
    public String getName();
}
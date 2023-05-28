package fr.aoc;

import java.util.ArrayList;
import java.util.List;

public interface Capteur {

    /**
     * Ajoute un observer au capteur
     * @param observer l'observer à ajouter
     */
    public void attach(ObserverDeCapteurAsync observer);

    /**
     * Supprime un observer du capteur
     * @param observer l'observer à supprimer
     */
    public void detach(ObserverDeCapteurAsync observer);

    /**
     * Retourne la valeur courante du capteur
     * @return la valeur courante du capteur
     */
    public Integer getValue(ObserverDeCapteurAsync observerDeCapteur);

    /**
     * Verouille le capteur
     */
    public void lock();

    /**
     * Déverouille le capteur
     */
    public void unlock();

    /**
     * Retourne l'état du capteur
     * @return true si le capteur est verouillé, false sinon
     */
    public boolean isLock();

    /**
     * Retourne la valeur courante du capteur
     * @return la valeur courante du capteur
     */
    public int getValue();

    /**
     * Incrémente la valeur du capteur
     */
    public void incValue();

    /**
     * Notifie les observers du capteur
     */
    public void tick();

    /**
     * Notifie les observers du capteur
     */
    public void notifyObservers();

    /**
     * Retourne la liste des valeurs envoyées
     * @return la liste des valeurs envoyées par le capteur
     */
    public ArrayList<Integer> getValues();

    /**
     * Retourne la liste des observers du capteur
     * @return la liste des observers du capteur
     */
    public List<ObserverDeCapteurAsync> getObservers();
}
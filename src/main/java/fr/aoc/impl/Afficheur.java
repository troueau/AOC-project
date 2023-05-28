package fr.aoc.impl;

import fr.aoc.CapteurAsync;
import fr.aoc.ObserverDeCapteur;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Afficheur implements ObserverDeCapteur {

    private String nomAfficheur;
    private static final Logger logger = Logger.getLogger(Afficheur.class.getName());
    private Handler handler;
    private ArrayList<Integer> values;

    public Afficheur(String nomAfficheur, Handler handler) {
        this.nomAfficheur = nomAfficheur;
        this.handler = handler;
        this.values = new ArrayList<>();
    }

    @Override
    public void update(CapteurAsync capteurAsync) {

        try {
            int value = capteurAsync.getValue().get();
            values.add(value);
            logger.info("L'afficheur \"" + nomAfficheur + "\" est en train d'afficher : " + value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Integer> getValues() {
        return values;
    }

    @Override
    public String getName() {
        return nomAfficheur;
    }

}
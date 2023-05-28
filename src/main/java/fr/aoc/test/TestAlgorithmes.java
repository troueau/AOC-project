package fr.aoc.test;

import fr.aoc.Capteur;
import fr.aoc.ObserverDeCapteur;
import fr.aoc.ObserverDeCapteurAsync;
import fr.aoc.impl.Afficheur;
import fr.aoc.impl.Canal;
import fr.aoc.impl.CapteurImpl;
import fr.aoc.strategy.AlgoDiffusion;
import fr.aoc.strategy.impl.DiffusionAtomique;
import fr.aoc.strategy.impl.DiffusionEpoque;
import fr.aoc.strategy.impl.DiffusionSequentielle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class TestAlgorithmes {

    private static final int FREQ_TICK = 500;
    private static final int NB_AFFICHEURS = 5;
    private static final int CORE_POOL_SIZE = 10;
    private static ScheduledExecutorService es;
    private static Capteur capteur;
    private static ArrayList<ObserverDeCapteur> observersDeCapteur;

    public void setUp(AlgoDiffusion algo) {
        // Creation d'un capteur avec l'algo de diffusion atomique
        capteur = new CapteurImpl(algo);

        // Creation d'un executor service
        es = Executors.newScheduledThreadPool(CORE_POOL_SIZE);

        // Creation d'un handler pour la console et pour les logs
        ConsoleHandler consoleHandler = new ConsoleHandler();

        // Initialisation d'une liste d'observer de capteur
        observersDeCapteur = new ArrayList<>();

        // Associer chaque afficheur avec chaque canal et chaque canal avec le capteur
        for(int i = 1; i <= NB_AFFICHEURS; i++) {
            ObserverDeCapteur observerDeCapteur = new Afficheur("Afficheur " + i, consoleHandler);
            observersDeCapteur.add(observerDeCapteur);
            ObserverDeCapteurAsync canal = new Canal(capteur, observerDeCapteur, es);
            capteur.attach(canal);
        }

    }

    @Test
    public void testAtomique() throws InterruptedException {
        // Creation de l'algorithme de diffusion atomique
        AlgoDiffusion algo = new DiffusionAtomique();

        // Appel de la fonction de setup
        setUp(algo);

        // Lancer le tick
        ScheduledFuture<?> scheduledFuture = es.scheduleAtFixedRate(() -> capteur.tick(), 0, FREQ_TICK, TimeUnit.MILLISECONDS);

        // Attendre X secondes
        Thread.sleep(10000);

        // Stop le tick
        scheduledFuture.cancel(false);

        // Attendre X secondes encore pour attendre que les threads se terminent
        Thread.sleep(10000);

        es.shutdown();

        // attendre 10 secondes que les threads se terminent et les fermer

        while(es.isTerminated()) {
            System.out.println("Waiting for threads to terminate");
        }

        es.shutdown();

        // Valeurs attendues
        ArrayList<Integer> expectedValues = capteur.getValues();

        // Verifier que les valeurs sont les memes
        for(ObserverDeCapteur observerDeCapteur : observersDeCapteur) {
            Logger.getGlobal().info(observerDeCapteur.getName() + " " + observerDeCapteur.getValues());
            Assertions.assertEquals(expectedValues, observerDeCapteur.getValues());
        }
    }

    @Test
    public void testEpoque() throws InterruptedException {
        AlgoDiffusion algo = new DiffusionEpoque();
        setUp(algo);

        ScheduledFuture<?> scheduledFuture = es.scheduleAtFixedRate(() -> capteur.tick(), 0, FREQ_TICK, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);

        scheduledFuture.cancel(true);

        Thread.sleep(10000);

        es.shutdown();

        ArrayList<Integer> valuesSent = capteur.getValues();

        for(ObserverDeCapteur observerDeCapteur : observersDeCapteur) {
            ArrayList<Integer> valuesDisplayed = observerDeCapteur.getValues();
            ArrayList<Integer> sortedValuesDisplayed = new ArrayList<>(valuesDisplayed);
            Collections.sort(sortedValuesDisplayed);
            Logger.getGlobal().info(observerDeCapteur.getName() + " " + valuesDisplayed);
            // Premier check savoir si la liste affichée est bien triée dans l'ordre croissant
            Assertions.assertEquals(sortedValuesDisplayed, valuesDisplayed);
            // Deuxième check savoir si la valeur affichée est bien dans la liste des valeurs envoyées
            Assertions.assertTrue(valuesSent.containsAll(valuesDisplayed));
        }
    }

    @Test
    public void testSequentielle() throws InterruptedException {
        AlgoDiffusion algo = new DiffusionSequentielle();
        setUp(algo);

        ScheduledFuture<?> scheduledFuture = es.scheduleAtFixedRate(() -> capteur.tick(), 0, FREQ_TICK, TimeUnit.MILLISECONDS);

        Thread.sleep(10000);

        scheduledFuture.cancel(true);

        Thread.sleep(10000);

        es.shutdown();

        ArrayList<Integer> valuesSent = capteur.getValues();
        ArrayList<Integer> firstValuesDisplayed = observersDeCapteur.remove(0).getValues();

        for(ObserverDeCapteur observerDeCapteur : observersDeCapteur) {
            ArrayList<Integer> valuesDisplayed = observerDeCapteur.getValues();
            // Premier check savoir si la liste affichée est égale à la première liste affichée
            Assertions.assertEquals(firstValuesDisplayed, valuesDisplayed);
            ArrayList<Integer> sortedValuesDisplayed = new ArrayList<>(valuesDisplayed);
            Logger.getGlobal().info(observerDeCapteur.getName() + " " + valuesDisplayed);
            Collections.sort(sortedValuesDisplayed);
            // Deuxième check savoir si la liste affichée est bien triée dans l'ordre croissant
            Assertions.assertEquals(sortedValuesDisplayed, valuesDisplayed);
            // Troisième check savoir si la valeur affichée est bien dans la liste des valeurs envoyées
            Assertions.assertTrue(valuesSent.containsAll(valuesDisplayed));
        }
    }
}

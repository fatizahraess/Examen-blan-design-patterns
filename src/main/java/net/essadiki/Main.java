package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.observer.Dashboard;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        Dashboard dashboard = new Dashboard();

        network.addObserver(dashboard);

        network.addLayer(new InputLayer(3));

        network.addLayer(
                new HiddenLayer(5, new Sigmoid())
        );

        network.addLayer(
                new OutputLayer(2, new Sigmoid())
        );

        double[] input = {1.0, 0.5, 0.8};

        System.out.println("===== ETAT INITIAL =====");

        System.out.println(
                "Etat : " + network.getStateName()
        );

        System.out.println("\n===== TEST AVANT ENTRAINEMENT =====");

        try {
            network.predict(input);
        } catch (IllegalStateException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        System.out.println("\n===== ENTRAINEMENT =====");

        network.fit();

        System.out.println("\n===== PREDICTION =====");

        double[] result = network.predict(input);

        System.out.println(
                "Input : " + Arrays.toString(input)
        );

        System.out.println(
                "Output : " + Arrays.toString(result)
        );

        System.out.println(
                "Etat final : " + network.getStateName()
        );
    }
}
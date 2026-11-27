package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.decorator.*;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.observer.Dashboard;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        network.addObserver(new Dashboard());

        // Couche d'entree normale
        network.addLayer(new InputLayer(3));

        // Couche cachee avec deux decorateurs
        network.addLayer(
                new TimingLayerDecorator(
                        new LoggingLayerDecorator(
                                new HiddenLayer(
                                        5, new Sigmoid()
                                )
                        )
                )
        );

        // Couche de sortie avec un decorateur
        network.addLayer(
                new LoggingLayerDecorator(
                        new OutputLayer(
                                2, new Sigmoid()
                        )
                )
        );

        System.out.println("===== ETAT INITIAL =====");

        System.out.println(network.getStateName());

        System.out.println("\n===== ENTRAINEMENT =====");

        network.fit();

        System.out.println("\n===== PREDICTION =====");

        double[] input = {1.0, 0.5, 0.8};

        double[] result = network.predict(input);

        System.out.println("\nResultat final : "
                + Arrays.toString(result));
    }
}
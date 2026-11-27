package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.decorator.*;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.observer.Dashboard;
import net.essadiki.neuralnetwork.security.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        double[] input = {1.0, 0.5, 0.8};

        try {

            System.out.println("===== TEST ADMIN =====");

            SecurityContext.setRole(Role.ADMIN);

            network.addObserver(new Dashboard());

            network.addLayer(new InputLayer(3));

            network.addLayer(
                    new TimingLayerDecorator(
                            new LoggingLayerDecorator(
                                    new HiddenLayer(
                                            5, new Sigmoid()
                                    )
                            )
                    )
            );

            network.addLayer(
                    new OutputLayer(2, new Sigmoid())
            );

            network.fit();

            System.out.println("\n===== TEST USER =====");

            SecurityContext.setRole(Role.USER);

            double[] result = network.predict(input);

            System.out.println("Prediction : "
                    + Arrays.toString(result));

            try {

                network.train();

            } catch (SecurityException e) {

                System.out.println("Erreur : "
                        + e.getMessage());
            }

            System.out.println("\n===== TEST GUEST =====");

            SecurityContext.setRole(Role.GUEST);

            try {

                network.predict(input);

            } catch (SecurityException e) {

                System.out.println("Erreur : "
                        + e.getMessage());
            }

        } finally {

            SecurityContext.logout();
        }
    }
}
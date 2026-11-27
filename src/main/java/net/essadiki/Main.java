package net.essadiki;

import net.essadiki.neuralnetwork.builder.NeuralNetworkBuilder;
import net.essadiki.neuralnetwork.factory.SigmoidFactory;
import net.essadiki.neuralnetwork.model.NeuralNetwork;
import net.essadiki.neuralnetwork.observer.Dashboard;
import net.essadiki.neuralnetwork.security.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        SecurityContext.setRole(Role.ADMIN);

        try {

            System.out.println("===== BUILDER ET FACTORY =====");

            NeuralNetwork network =
                    new NeuralNetworkBuilder()
                            .observer(new Dashboard())
                            .inputLayer(3)
                            .hiddenLayer(
                                    5, new SigmoidFactory()
                            )
                            .outputLayer(
                                    2, new SigmoidFactory()
                            )
                            .build();

            double[][] inputs = {
                    {1.0, 0.5, 0.8}
            };

            double[][] targets = {
                    {0.9, 0.1}
            };

            network.setTrainingData(
                    inputs, targets, 100, 0.5
            );

            System.out.println("\n===== ENTRAINEMENT =====");

            network.fit();

            SecurityContext.setRole(Role.USER);

            System.out.println("\n===== PREDICTION =====");

            double[] result =
                    network.predict(inputs[0]);

            System.out.println(
                    "Input : " + Arrays.toString(inputs[0])
            );

            System.out.println(
                    "Target : " + Arrays.toString(targets[0])
            );

            System.out.println(
                    "Prediction : " + Arrays.toString(result)
            );

            System.out.println(
                    "Etat final : " + network.getStateName()
            );

        } finally {

            SecurityContext.logout();
        }
    }
}
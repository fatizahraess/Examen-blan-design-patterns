package net.essadiki;
import net.essadiki.neuralnetwork.activation.*;
import net.essadiki.neuralnetwork.adapter.*;

public class Main {

    public static void main(String[] args) {

        double x = 2.0;

        System.out.println("===== PATTERN STRATEGY =====");

        ActivationFunction activation = new Sigmoid();
        System.out.println("Sigmoid : " + activation.activate(x));

        activation = new Identity();
        System.out.println("Identity : " + activation.activate(x));

        activation = new ReLU();
        System.out.println("ReLU : " + activation.activate(x));

        System.out.println("\n===== PATTERN ADAPTER =====");

        activation = new ActivationAdapter(
                new LegacyActivation()
        );

        System.out.println("Legacy : " + activation.activate(x));
    }
}
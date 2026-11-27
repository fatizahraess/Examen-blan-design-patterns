package net.essadiki.neuralnetwork.builder;

import net.essadiki.neuralnetwork.activation.ActivationFunction;
import net.essadiki.neuralnetwork.factory.ActivationFactory;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.observer.NetworkObserver;

public class NeuralNetworkBuilder {

    private final NeuralNetwork network =
            new NeuralNetwork();

    private boolean inputAdded = false;
    private boolean outputAdded = false;
    private boolean built = false;

    private void checkBuilder() {
        if (built) {
            throw new IllegalStateException(
                    "Le reseau est deja construit"
            );
        }
    }

    public NeuralNetworkBuilder inputLayer(int size) {

        checkBuilder();

        if (inputAdded) {
            throw new IllegalStateException(
                    "Une couche d'entree existe deja"
            );
        }

        network.addLayer(new InputLayer(size));
        inputAdded = true;

        return this;
    }

    public NeuralNetworkBuilder hiddenLayer(
            int size, ActivationFactory factory) {

        checkBuilder();

        if (!inputAdded || outputAdded) {
            throw new IllegalStateException(
                    "Position de la couche cachee invalide"
            );
        }

        ActivationFunction activation =
                factory.createActivation();

        network.addLayer(
                new HiddenLayer(size, activation)
        );

        return this;
    }

    public NeuralNetworkBuilder outputLayer(
            int size, ActivationFactory factory) {

        checkBuilder();

        if (!inputAdded || outputAdded) {
            throw new IllegalStateException(
                    "Position de la couche de sortie invalide"
            );
        }

        network.addLayer(
                new OutputLayer(
                        size,
                        factory.createActivation()
                )
        );

        outputAdded = true;

        return this;
    }

    public NeuralNetworkBuilder observer(
            NetworkObserver observer) {

        checkBuilder();
        network.addObserver(observer);

        return this;
    }

    public NeuralNetwork build() {

        checkBuilder();

        if (!inputAdded || !outputAdded) {
            throw new IllegalStateException(
                    "Architecture incomplete"
            );
        }

        built = true;

        return network;
    }
}
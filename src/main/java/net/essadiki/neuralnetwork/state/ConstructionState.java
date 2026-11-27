package net.essadiki.neuralnetwork.state;

import net.essadiki.neuralnetwork.model.NeuralNetwork;

public class ConstructionState implements NetworkState {

    @Override
    public void fit(NeuralNetwork network) {
        train(network);
    }

    @Override
    public void train(NeuralNetwork network) {

        network.setState(new TrainingState());
        network.train();
    }

    @Override
    public double[] predict(NeuralNetwork network,
                            double[] input) {

        throw new IllegalStateException(
                "Le reseau doit etre entraine avant la prediction"
        );
    }

    @Override
    public String getName() {
        return "Construction";
    }
}
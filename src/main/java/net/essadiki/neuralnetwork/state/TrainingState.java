package net.essadiki.neuralnetwork.state;

import net.essadiki.neuralnetwork.model.NeuralNetwork;

public class TrainingState implements NetworkState {

    @Override
    public void fit(NeuralNetwork network) {
        throw new IllegalStateException(
                "Entrainement deja en cours"
        );
    }

    @Override
    public void train(NeuralNetwork network) {

        network.performTraining();
        network.setState(new TrainedState());
    }

    @Override
    public double[] predict(NeuralNetwork network,
                            double[] input) {

        throw new IllegalStateException(
                "Prediction interdite pendant l'entrainement"
        );
    }

    @Override
    public String getName() {
        return "Training";
    }
}
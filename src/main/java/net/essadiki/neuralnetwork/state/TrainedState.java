package net.essadiki.neuralnetwork.state;

import net.essadiki.neuralnetwork.model.NeuralNetwork;

public class TrainedState implements NetworkState {

    @Override
    public void fit(NeuralNetwork network) {
        throw new IllegalStateException(
                "Le reseau est deja entraine"
        );
    }

    @Override
    public void train(NeuralNetwork network) {

        network.setState(new TrainingState());
        network.train();
    }

    @Override
    public double[] predict(NeuralNetwork network,
                            double[] input) {

        return network.forwardPropagation(input);
    }

    @Override
    public String getName() {
        return "Trained";
    }
}
package net.essadiki.neuralnetwork.state;

import net.essadiki.neuralnetwork.model.NeuralNetwork;

public interface NetworkState {

    void fit(NeuralNetwork network);

    void train(NeuralNetwork network);

    double[] predict(NeuralNetwork network, double[] input);

    String getName();
}
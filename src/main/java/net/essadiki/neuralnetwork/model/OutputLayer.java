package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

public class OutputLayer extends net.essadiki.neuralnetwork.model.Layer {

    public OutputLayer(int numberOfNeurons,
                       ActivationFunction activationFunction) {

        super(numberOfNeurons, activationFunction);
    }

    @Override
    public double[] forward(double[] input) {
        return computeOutputs();
    }
}
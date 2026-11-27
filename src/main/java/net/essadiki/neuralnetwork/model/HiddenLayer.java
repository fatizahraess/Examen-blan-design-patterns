package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

public class HiddenLayer extends net.essadiki.neuralnetwork.model.Layer {

    public HiddenLayer(int numberOfNeurons,
                       ActivationFunction activationFunction) {

        super(numberOfNeurons, activationFunction);
    }

    @Override
    public double[] forward(double[] input) {
        return computeOutputs();
    }
}
package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class Layer {

    protected final List<net.essadiki.neuralnetwork.model.Neuron> neurons =
            new ArrayList<>();

    protected Layer() {
    }

    protected Layer(int numberOfNeurons,
                    ActivationFunction activationFunction) {

        if (numberOfNeurons <= 0) {
            throw new IllegalArgumentException(
                    "Le nombre de neurones doit etre positif"
            );
        }

        for (int i = 0; i < numberOfNeurons; i++) {
            neurons.add(
                    new net.essadiki.neuralnetwork.model.Neuron(0.0, activationFunction)
            );
        }
    }

    public abstract double[] forward(double[] input);

    protected double[] computeOutputs() {

        double[] outputs = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).compute();
        }

        return outputs;
    }

    public List<net.essadiki.neuralnetwork.model.Neuron> getNeurons() {
        return List.copyOf(neurons);
    }

    public int size() {
        return neurons.size();
    }
}
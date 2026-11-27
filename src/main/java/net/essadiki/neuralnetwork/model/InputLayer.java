package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.activation.Identity;

public class InputLayer extends net.essadiki.neuralnetwork.model.Layer {

    public InputLayer(int numberOfNeurons) {
        super(numberOfNeurons, new Identity());
    }

    @Override
    public double[] forward(double[] input) {

        if (input.length != neurons.size()) {
            throw new IllegalArgumentException(
                    "Dimension des entrees incorrecte"
            );
        }

        for (int i = 0; i < input.length; i++) {
            neurons.get(i).setOutput(input[i]);
        }

        return input.clone();
    }
}
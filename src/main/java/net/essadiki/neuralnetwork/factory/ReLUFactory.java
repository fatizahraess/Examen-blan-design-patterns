package net.essadiki.neuralnetwork.factory;

import net.essadiki.neuralnetwork.activation.*;

public class ReLUFactory extends ActivationFactory {

    @Override
    public ActivationFunction createActivation() {
        return new ReLU();
    }
}
package net.essadiki.neuralnetwork.factory;

import net.essadiki.neuralnetwork.activation.*;

public class SigmoidFactory extends ActivationFactory {

    @Override
    public ActivationFunction createActivation() {
        return new Sigmoid();
    }
}
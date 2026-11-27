package net.essadiki.neuralnetwork.factory;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

public abstract class ActivationFactory {

    public abstract ActivationFunction createActivation();
}
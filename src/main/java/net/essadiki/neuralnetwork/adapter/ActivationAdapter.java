package net.essadiki.neuralnetwork.adapter;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

public class ActivationAdapter implements ActivationFunction {

    private final LegacyActivation legacyActivation;

    public ActivationAdapter(LegacyActivation legacyActivation) {
        this.legacyActivation = legacyActivation;
    }

    @Override
    public double activate(double x) {
        return legacyActivation.calculate(x);
    }
}
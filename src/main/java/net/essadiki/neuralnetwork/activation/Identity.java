package net.essadiki.neuralnetwork.activation;
public class Identity implements ActivationFunction {

    @Override
    public double activate(double x) {
        return x;
    }
}
package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.activation.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private double bias;
    private double output;

    private ActivationFunction activationFunction;

    private final List<Connection> connections =
            new ArrayList<>();

    public Neuron(double bias,
                  ActivationFunction activationFunction) {
        this.bias = bias;
        this.activationFunction = activationFunction;
    }

    public void addConnection(Connection connection) {
        if (connection.getDestination() != this) {
            throw new IllegalArgumentException(
                    "Destination incorrecte"
            );
        }

        connections.add(connection);
    }

    public double compute() {

        double sum = bias;

        for (Connection connection : connections) {
            sum += connection.getWeightedValue();
        }

        output = activationFunction.activate(sum);

        return output;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setActivationFunction(
            ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public List<Connection> getConnections() {
        return List.copyOf(connections);
    }
}
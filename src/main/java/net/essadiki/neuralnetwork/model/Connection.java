package net.essadiki.neuralnetwork.model;

public class Connection {

    private double weight;
    private final net.essadiki.neuralnetwork.model.Neuron source;
    private final net.essadiki.neuralnetwork.model.Neuron destination;

    public Connection(net.essadiki.neuralnetwork.model.Neuron source, net.essadiki.neuralnetwork.model.Neuron destination,
                      double weight) {
        this.source = source;
        this.destination = destination;
        setWeight(weight);
    }

    public double getWeightedValue() {
        return source.getOutput() * weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (!Double.isFinite(weight) || weight < 0 || weight > 1) {
            throw new IllegalArgumentException(
                    "Le poids doit etre compris entre 0 et 1"
            );
        }
        this.weight = weight;
    }

    public net.essadiki.neuralnetwork.model.Neuron getSource() {
        return source;
    }

    public net.essadiki.neuralnetwork.model.Neuron getDestination() {
        return destination;
    }
}
package net.essadiki.neuralnetwork.model;

import net.essadiki.neuralnetwork.state.*;
import net.essadiki.neuralnetwork.observer.NetworkObserver;
import net.essadiki.neuralnetwork.decorator.LayerDecorator;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final List<Layer> layers = new ArrayList<>();

    private final List<NetworkObserver> observers =
            new ArrayList<>();

    private NetworkState state = new ConstructionState();

    // Gestion des couches

    public void addLayer(Layer layer) {

        if (!(state instanceof ConstructionState)) {
            throw new IllegalStateException(
                    "Modification interdite hors construction"
            );
        }

        if (!layers.isEmpty()) {

            Layer previousLayer =
                    layers.get(layers.size() - 1);

            connectLayers(previousLayer, layer);
        }

        layers.add(layer);
    }
    private Layer unwrapLayer(Layer layer) {

        while (layer instanceof LayerDecorator decorator) {
            layer = decorator.getWrappedLayer();
        }

        return layer;
    }
    private void connectLayers(Layer source,
                               Layer destination) {

        for (Neuron destinationNeuron :
                destination.getNeurons()) {

            for (Neuron sourceNeuron :
                    source.getNeurons()) {

                Connection connection = new Connection(
                        sourceNeuron,
                        destinationNeuron,
                        0.5
                );

                destinationNeuron.addConnection(connection);
            }
        }
    }

    // Pattern State

    public void fit() {
        validateArchitecture();
        state.fit(this);
    }

    public void train() {
        validateArchitecture();
        state.train(this);
    }

    public double[] predict(double[] input) {
        return state.predict(this, input);
    }

    public void setState(NetworkState state) {

        this.state = state;
        notifyObservers();
    }

    public String getStateName() {
        return state.getName();
    }

    // Pattern Observer

    public void addObserver(NetworkObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(NetworkObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {

        for (NetworkObserver observer : observers) {
            observer.update(state.getName());
        }
    }

    // Entrainement

    public void performTraining() {

        if (!(state instanceof TrainingState)) {
            throw new IllegalStateException(
                    "Le reseau doit etre en mode Training"
            );
        }

        System.out.println("Entrainement en cours...");

        // Simulation de l'entrainement

        System.out.println("Entrainement termine.");
    }

    // Propagation avant

    public double[] forwardPropagation(double[] input) {

        if (!(state instanceof TrainedState)) {
            throw new IllegalStateException(
                    "Le reseau doit etre entraine"
            );
        }

        double[] result = input;

        for (Layer layer : layers) {
            result = layer.forward(result);
        }

        return result;
    }

    public List<Layer> getLayers() {
        return List.copyOf(layers);
    }

    private void validateArchitecture() {

        if (layers.size() < 2) {
            throw new IllegalStateException(
                    "Architecture du reseau invalide"
            );
        }

        Layer first = unwrapLayer(layers.get(0));

        Layer last = unwrapLayer(
                layers.get(layers.size() - 1)
        );

        if (!(first instanceof InputLayer)
                || !(last instanceof OutputLayer)) {

            throw new IllegalStateException(
                    "Architecture du reseau invalide"
            );
        }

        for (int i = 1; i < layers.size() - 1; i++) {

            if (!(unwrapLayer(layers.get(i))
                    instanceof HiddenLayer)) {

                throw new IllegalStateException(
                        "Les couches intermediaires doivent etre cachees"
                );
            }
        }
    }}
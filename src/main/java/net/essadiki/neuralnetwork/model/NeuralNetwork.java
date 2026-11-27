package net.essadiki.neuralnetwork.model;
import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.state.*;
import net.essadiki.neuralnetwork.observer.NetworkObserver;
import net.essadiki.neuralnetwork.decorator.LayerDecorator;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private double[][] trainingInputs;
    private double[][] trainingTargets;

    private int epochs = 100;
    private double learningRate = 0.1;
    private final List<Layer> layers = new ArrayList<>();

    private final List<NetworkObserver> observers =
            new ArrayList<>();

    private NetworkState state = new ConstructionState();

    // Gestion des couches
    public void setTrainingData(
            double[][] inputs,
            double[][] targets,
            int epochs,
            double learningRate) {

        validateArchitecture();

        if (state instanceof TrainingState) {
            throw new IllegalStateException(
                    "Entrainement deja en cours"
            );
        }

        if (inputs == null || targets == null
                || inputs.length == 0
                || inputs.length != targets.length
                || epochs <= 0
                || !Double.isFinite(learningRate)
                || learningRate <= 0) {

            throw new IllegalArgumentException(
                    "Donnees d'entrainement invalides"
            );
        }

        int inputSize = layers.get(0).size();

        int outputSize =
                layers.get(layers.size() - 1).size();

        double[][] inputCopy = new double[inputs.length][];
        double[][] targetCopy = new double[targets.length][];

        for (int i = 0; i < inputs.length; i++) {

            if (inputs[i] == null || targets[i] == null
                    || inputs[i].length != inputSize
                    || targets[i].length != outputSize) {

                throw new IllegalArgumentException(
                        "Dimensions incorrectes"
                );
            }

            for (double value : inputs[i]) {
                if (!Double.isFinite(value)) {
                    throw new IllegalArgumentException(
                            "Entree invalide"
                    );
                }
            }

            for (double target : targets[i]) {
                if (!Double.isFinite(target)
                        || target < 0 || target > 1) {
                    throw new IllegalArgumentException(
                            "Cible invalide"
                    );
                }
            }

            inputCopy[i] = inputs[i].clone();
            targetCopy[i] = targets[i].clone();
        }

        this.trainingInputs = inputCopy;
        this.trainingTargets = targetCopy;
        this.epochs = epochs;
        this.learningRate = learningRate;
    }
    private double[] computeForward(double[] input) {

        double[] result = input;

        for (Layer layer : layers) {
            result = layer.forward(result);
        }

        return result;
    }
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

        if (trainingInputs == null) {

            System.out.println(
                    "Mode demonstration : aucun apprentissage"
            );

            return;
        }

        Layer outputLayer =
                layers.get(layers.size() - 1);

        for (Neuron neuron : outputLayer.getNeurons()) {

            if (!(neuron.getActivationFunction()
                    instanceof Sigmoid)) {

                throw new IllegalStateException(
                        "L'apprentissage exige une sortie Sigmoid"
                );
            }
        }

        for (int epoch = 0; epoch < epochs; epoch++) {

            double totalError = 0;

            for (int i = 0; i < trainingInputs.length; i++) {

                double[] prediction =
                        computeForward(trainingInputs[i]);

                double[] target = trainingTargets[i];

                for (int j = 0; j < outputLayer.size(); j++) {

                    Neuron neuron =
                            outputLayer.getNeurons().get(j);

                    double error = target[j] - prediction[j];

                    totalError += 0.5 * error * error;

                    double delta = error
                            * prediction[j]
                            * (1 - prediction[j]);

                    for (Connection connection :
                            neuron.getConnections()) {

                        double newWeight =
                                connection.getWeight()
                                        + learningRate
                                        * delta
                                        * connection.getSource()
                                        .getOutput();

                        newWeight = Math.max(
                                0, Math.min(1, newWeight)
                        );

                        connection.setWeight(newWeight);
                    }

                    neuron.setBias(
                            neuron.getBias()
                                    + learningRate * delta
                    );
                }
            }

            if (epoch % 10 == 0 || epoch == epochs - 1) {

                System.out.println(
                        "Epoch " + (epoch + 1)
                                + " | Erreur moyenne : "
                                + totalError / trainingInputs.length
                );
            }
        }

        System.out.println("Entrainement termine.");
    }
    // Propagation avant

    public double[] forwardPropagation(double[] input) {

        if (!(state instanceof TrainedState)) {
            throw new IllegalStateException(
                    "Le reseau doit etre entraine"
            );
        }

        return computeForward(input);
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
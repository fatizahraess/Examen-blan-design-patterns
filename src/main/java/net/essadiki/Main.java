package net.essadiki;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.model.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        InputLayer inputLayer = new InputLayer(3);

        HiddenLayer hiddenLayer =
                new HiddenLayer(5, new Sigmoid());

        OutputLayer outputLayer =
                new OutputLayer(2, new Sigmoid());

        connectLayers(inputLayer, hiddenLayer);
        connectLayers(hiddenLayer, outputLayer);

        double[] input = {1.0, 0.5, 0.8};

        System.out.println("===== NEURAL NETWORK =====");

        double[] inputValues = inputLayer.forward(input);

        System.out.println(
                "Input : " + Arrays.toString(inputValues)
        );

        double[] hiddenValues =
                hiddenLayer.forward(inputValues);

        System.out.println(
                "Hidden : " + Arrays.toString(hiddenValues)
        );

        double[] outputValues =
                outputLayer.forward(hiddenValues);

        System.out.println(
                "Output : " + Arrays.toString(outputValues)
        );
    }

    private static void connectLayers(Layer source,
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
}
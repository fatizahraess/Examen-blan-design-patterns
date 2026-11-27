package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Identity;
import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.model.Connection;
import net.essadiki.neuralnetwork.model.Neuron;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuronTest {

    @Test
    void testNeuronComputation() {

        Neuron source = new Neuron(0, new Identity());
        source.setOutput(2.0);

        Neuron destination = new Neuron(0.5, new Identity());

        Connection connection =
                new Connection(source, destination, 0.5);

        destination.addConnection(connection);

        assertEquals(1.5, destination.compute(), 0.0001);
    }

    @Test
    void testInvalidWeight() {

        Neuron source = new Neuron(0, new Identity());
        Neuron destination = new Neuron(0, new Identity());

        assertThrows(
                IllegalArgumentException.class,
                () -> new Connection(source, destination, 1.5)
        );
    }

    @Test
    void testChangeActivationFunction() {

        Neuron neuron = new Neuron(0, new Identity());

        assertEquals(0.0, neuron.compute(), 0.0001);

        neuron.setActivationFunction(new Sigmoid());

        assertEquals(0.5, neuron.compute(), 0.0001);
    }
}
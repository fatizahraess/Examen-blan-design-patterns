package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.*;
import net.essadiki.neuralnetwork.adapter.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivationFunctionTest {

    @Test
    void testSigmoid() {
        ActivationFunction sigmoid = new Sigmoid();
        assertEquals(0.5, sigmoid.activate(0), 0.0001);
    }

    @Test
    void testIdentity() {
        ActivationFunction identity = new Identity();
        assertEquals(5.0, identity.activate(5.0), 0.0001);
    }

    @Test
    void testReLU() {
        ActivationFunction relu = new ReLU();
        assertEquals(0.0, relu.activate(-5), 0.0001);
        assertEquals(5.0, relu.activate(5), 0.0001);
    }

    @Test
    void testAdapter() {
        ActivationFunction adapter =
                new ActivationAdapter(new LegacyActivation());

        assertEquals(
                Math.tanh(2),
                adapter.activate(2),
                0.0001
        );
    }
}
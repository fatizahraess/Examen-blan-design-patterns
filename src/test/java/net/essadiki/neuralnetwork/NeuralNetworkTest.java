package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.observer.Dashboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeuralNetworkTest {

    private NeuralNetwork network;

    @BeforeEach
    void setUp() {

        network = new NeuralNetwork();

        network.addLayer(new InputLayer(3));

        network.addLayer(
                new HiddenLayer(5, new Sigmoid())
        );

        network.addLayer(
                new OutputLayer(2, new Sigmoid())
        );
    }

    @Test
    void testInitialState() {

        assertEquals(
                "Construction",
                network.getStateName()
        );
    }

    @Test
    void testPredictionBeforeTraining() {

        assertThrows(
                IllegalStateException.class,
                () -> network.predict(
                        new double[]{1.0, 0.5, 0.8}
                )
        );
    }

    @Test
    void testStateTransition() {

        network.fit();

        assertEquals(
                "Trained",
                network.getStateName()
        );
    }

    @Test
    void testObserverNotification() {

        Dashboard dashboard = new Dashboard();

        network.addObserver(dashboard);

        network.fit();

        assertEquals(2, dashboard.getStateChanges());
    }

    @Test
    void testPredictionAfterTraining() {

        network.fit();

        double[] result = network.predict(
                new double[]{1.0, 0.5, 0.8}
        );

        assertEquals(2, result.length);

        double hidden = 1.0 /
                (1.0 + Math.exp(-1.15));

        double expected = 1.0 /
                (1.0 + Math.exp(-2.5 * hidden));

        assertEquals(expected, result[0], 0.0001);
        assertEquals(expected, result[1], 0.0001);
    }

    @Test
    void testRetraining() {

        network.fit();
        network.train();

        assertEquals(
                "Trained",
                network.getStateName()
        );
    }
}
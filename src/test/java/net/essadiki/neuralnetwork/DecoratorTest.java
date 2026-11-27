package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.decorator.*;
import net.essadiki.neuralnetwork.model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    @Test
    void testLoggingDecorator() {

        Layer layer = new InputLayer(3);

        Layer decorated =
                new LoggingLayerDecorator(layer);

        double[] input = {1.0, 0.5, 0.8};

        assertArrayEquals(
                layer.forward(input),
                decorated.forward(input),
                0.0001
        );
    }

    @Test
    void testTimingDecorator() {

        Layer layer = new InputLayer(3);

        Layer decorated =
                new TimingLayerDecorator(layer);

        double[] input = {1.0, 0.5, 0.8};

        assertArrayEquals(
                layer.forward(input),
                decorated.forward(input),
                0.0001
        );
    }

    @Test
    void testCombinedDecorators() {

        Layer layer = new InputLayer(3);

        Layer decorated = new TimingLayerDecorator(
                new LoggingLayerDecorator(layer)
        );

        assertEquals(3, decorated.size());

        assertEquals(
                layer.getNeurons(),
                decorated.getNeurons()
        );

        double[] input = {1.0, 0.5, 0.8};

        assertArrayEquals(
                input,
                decorated.forward(input),
                0.0001
        );
    }

    @Test
    void testDecoratedNeuralNetwork() {

        NeuralNetwork network = new NeuralNetwork();

        network.addLayer(
                new LoggingLayerDecorator(
                        new InputLayer(3)
                )
        );

        network.addLayer(
                new TimingLayerDecorator(
                        new HiddenLayer(5, new Sigmoid())
                )
        );

        network.addLayer(
                new LoggingLayerDecorator(
                        new OutputLayer(2, new Sigmoid())
                )
        );

        network.fit();

        double[] result = network.predict(
                new double[]{1.0, 0.5, 0.8}
        );

        double hidden = 1.0 /
                (1.0 + Math.exp(-1.15));

        double expected = 1.0 /
                (1.0 + Math.exp(-2.5 * hidden));

        assertArrayEquals(
                new double[]{expected, expected},
                result,
                0.0001
        );

        assertEquals("Trained", network.getStateName());
    }
}
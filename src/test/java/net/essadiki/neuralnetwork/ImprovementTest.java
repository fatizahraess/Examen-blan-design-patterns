package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.*;
import net.essadiki.neuralnetwork.builder.NeuralNetworkBuilder;
import net.essadiki.neuralnetwork.factory.*;
import net.essadiki.neuralnetwork.model.NeuralNetwork;
import net.essadiki.neuralnetwork.security.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ImprovementTest {

    @BeforeEach
    void setUp() {
        SecurityContext.setRole(Role.ADMIN);
    }

    @AfterEach
    void tearDown() {
        SecurityContext.logout();
    }

    @Test
    void testFactoryMethod() {

        assertInstanceOf(
                Sigmoid.class,
                new SigmoidFactory().createActivation()
        );

        assertInstanceOf(
                Identity.class,
                new IdentityFactory().createActivation()
        );

        assertInstanceOf(
                ReLU.class,
                new ReLUFactory().createActivation()
        );
    }

    @Test
    void testBuilder() {

        NeuralNetwork network =
                new NeuralNetworkBuilder()
                        .inputLayer(3)
                        .hiddenLayer(5, new SigmoidFactory())
                        .outputLayer(2, new SigmoidFactory())
                        .build();

        assertEquals(3, network.getLayers().size());

        assertEquals(
                "Construction",
                network.getStateName()
        );
    }

    @Test
    void testIncompleteNetwork() {

        assertThrows(
                IllegalStateException.class,
                () -> new NeuralNetworkBuilder()
                        .inputLayer(3)
                        .build()
        );
    }

    @Test
    void testTrainingImprovement() {

        NeuralNetwork network =
                new NeuralNetworkBuilder()
                        .inputLayer(3)
                        .hiddenLayer(5, new SigmoidFactory())
                        .outputLayer(2, new SigmoidFactory())
                        .build();

        double[] input = {1.0, 0.5, 0.8};

        double[] target = {0.9, 0.1};

        network.fit();

        double[] before = network.predict(input);

        double errorBefore =
                Math.pow(target[0] - before[0], 2)
                        + Math.pow(target[1] - before[1], 2);

        network.setTrainingData(
                new double[][]{input},
                new double[][]{target},
                100,
                0.5
        );

        network.train();

        double[] after = network.predict(input);

        double errorAfter =
                Math.pow(target[0] - after[0], 2)
                        + Math.pow(target[1] - after[1], 2);

        assertTrue(errorAfter < errorBefore);
    }

    @Test
    void testUserCannotConfigureTraining() {

        NeuralNetwork network =
                new NeuralNetworkBuilder()
                        .inputLayer(3)
                        .outputLayer(2, new SigmoidFactory())
                        .build();

        SecurityContext.setRole(Role.USER);

        assertThrows(
                SecurityException.class,
                () -> network.setTrainingData(
                        new double[][]{{1, 0, 1}},
                        new double[][]{{0.9, 0.1}},
                        100,
                        0.5
                )
        );
    }
}
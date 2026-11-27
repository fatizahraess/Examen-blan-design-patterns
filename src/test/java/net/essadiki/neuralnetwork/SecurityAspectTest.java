package net.essadiki.neuralnetwork;

import net.essadiki.neuralnetwork.activation.Sigmoid;
import net.essadiki.neuralnetwork.model.*;
import net.essadiki.neuralnetwork.security.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityAspectTest {

    private NeuralNetwork network;

    @BeforeEach
    void setUp() {

        SecurityContext.setRole(Role.ADMIN);

        network = new NeuralNetwork();

        network.addLayer(new InputLayer(3));

        network.addLayer(
                new HiddenLayer(5, new Sigmoid())
        );

        network.addLayer(
                new OutputLayer(2, new Sigmoid())
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContext.logout();
    }

    @Test
    void testAdminCanTrain() {

        SecurityContext.setRole(Role.ADMIN);

        assertDoesNotThrow(() -> network.fit());

        assertEquals(
                "Trained",
                network.getStateName()
        );
    }

    @Test
    void testUserCannotTrain() {

        SecurityContext.setRole(Role.USER);

        assertThrows(
                SecurityException.class,
                () -> network.train()
        );
    }

    @Test
    void testUserCanPredict() {

        network.fit();

        SecurityContext.setRole(Role.USER);

        assertDoesNotThrow(
                () -> network.predict(
                        new double[]{1.0, 0.5, 0.8}
                )
        );
    }

    @Test
    void testGuestCannotPredict() {

        network.fit();

        SecurityContext.setRole(Role.GUEST);

        assertThrows(
                SecurityException.class,
                () -> network.predict(
                        new double[]{1.0, 0.5, 0.8}
                )
        );
    }

    @Test
    void testGuestCannotAddLayer() {

        SecurityContext.setRole(Role.GUEST);

        assertThrows(
                SecurityException.class,
                () -> network.addLayer(
                        new HiddenLayer(3, new Sigmoid())
                )
        );
    }
}
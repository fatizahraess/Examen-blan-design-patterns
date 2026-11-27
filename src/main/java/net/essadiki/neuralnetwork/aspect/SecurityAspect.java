
package net.essadiki.neuralnetwork.aspect;

import net.essadiki.neuralnetwork.security.Role;
import net.essadiki.neuralnetwork.security.SecurityContext;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclarePrecedence;

@Aspect
@DeclarePrecedence(
        "net.essadiki.neuralnetwork.aspect.SecurityAspect, " +
                "net.essadiki.neuralnetwork.aspect.LoggingAspect, *"
)
public class SecurityAspect {

    // Operations reservees aux administrateurs

    @Before(
            "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.addLayer(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.fit(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.train(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.setState(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.performTraining(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.setTrainingData(..))"
    )
    public void checkAdminPermission() {

        if (SecurityContext.getRole() != Role.ADMIN) {

            System.out.println(
                    "[SECURITY] Acces administrateur refuse"
            );

            throw new SecurityException(
                    "Operation reservee aux administrateurs"
            );
        }

        System.out.println(
                "[SECURITY] Acces administrateur autorise"
        );
    }

    // Prediction autorisee aux administrateurs et utilisateurs

    @Before(
            "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.predict(..)) || "
                    + "execution(* net.essadiki.neuralnetwork.model."
                    + "NeuralNetwork.forwardPropagation(..))"
    )
    public void checkPredictionPermission() {

        Role role = SecurityContext.getRole();

        if (role != Role.ADMIN && role != Role.USER) {

            System.out.println(
                    "[SECURITY] Prediction refusee"
            );

            throw new SecurityException(
                    "Vous ne pouvez pas effectuer une prediction"
            );
        }

        System.out.println(
                "[SECURITY] Prediction autorisee"
        );
    }
}

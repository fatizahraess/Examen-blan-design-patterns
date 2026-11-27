package net.essadiki.neuralnetwork.factory;

import net.essadiki.neuralnetwork.activation.*;

public class IdentityFactory extends ActivationFactory {

    @Override
    public ActivationFunction createActivation() {
        return new Identity();
    }
}
package net.essadiki.neuralnetwork.decorator;

import net.essadiki.neuralnetwork.model.Layer;

import java.util.Arrays;

public class LoggingLayerDecorator extends LayerDecorator {

    public LoggingLayerDecorator(Layer layer) {
        super(layer);
    }

    @Override
    public double[] forward(double[] input) {

        System.out.println("[LOG] Couche : "
                + layer.getClass().getSimpleName());

        System.out.println("[LOG] Input : "
                + Arrays.toString(input));

        double[] output = super.forward(input);

        System.out.println("[LOG] Output : "
                + Arrays.toString(output));

        return output;
    }
}
package net.essadiki.neuralnetwork.decorator;

import net.essadiki.neuralnetwork.model.Layer;

public class TimingLayerDecorator extends LayerDecorator {

    public TimingLayerDecorator(Layer layer) {
        super(layer);
    }

    @Override
    public double[] forward(double[] input) {

        long start = System.nanoTime();

        double[] output = super.forward(input);

        long end = System.nanoTime();

        double duration = (end - start) / 1_000_000.0;

        System.out.printf(
                "[TIMING] %s : %.4f ms%n",
                layer.getClass().getSimpleName(),
                duration
        );

        return output;
    }
}
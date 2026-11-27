package net.essadiki.neuralnetwork.decorator;

import net.essadiki.neuralnetwork.model.Layer;
import net.essadiki.neuralnetwork.model.Neuron;

import java.util.List;

public abstract class LayerDecorator extends Layer {

    protected final Layer layer;

    public LayerDecorator(Layer layer) {
        this.layer = layer;
    }

    @Override
    public double[] forward(double[] input) {
        return layer.forward(input);
    }

    @Override
    public List<Neuron> getNeurons() {
        return layer.getNeurons();
    }

    @Override
    public int size() {
        return layer.size();
    }

    public Layer getWrappedLayer() {
        return layer;
    }
}
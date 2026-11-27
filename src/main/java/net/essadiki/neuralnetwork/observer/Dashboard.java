package net.essadiki.neuralnetwork.observer;

public class Dashboard implements NetworkObserver {

    private int stateChanges = 0;

    @Override
    public void update(String state) {

        stateChanges++;

        System.out.println(
                "[Dashboard] Nouvel etat : " + state
        );

        System.out.println(
                "[Dashboard] Nombre de changements : "
                        + stateChanges
        );
    }

    public int getStateChanges() {
        return stateChanges;
    }
}
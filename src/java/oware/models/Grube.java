package oware.models;

public class Grube {
    private int seeds = 4;

    // Getter Methode
    public int getSeeds() {
        return seeds;
    }

    // Erhöht Anzahl Seeds in der Grube um 1
    public void incrementSeed() {
        seeds++;
    }

    // Setzt Anzahl Seeds in der Grube auf 0
    public void reset() {
        this.seeds = 0;
    }

}
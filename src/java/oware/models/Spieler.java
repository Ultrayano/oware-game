package oware.models;

public class Spieler {

    private int minReichweite;
    private int maxReichweite;
    private int punktzahl;

    public void setRange(int min, int max) {
        this.minReichweite = min;
        this.maxReichweite = max;
    }

    public int getMinReichweite() {
        return minReichweite;
    }

    public int getMaxReichweite() {
        return maxReichweite;
    }

    // Berechnet ob der Spieler in Reichweite ist um zu spielen oder um Punkte zu sammeln
    public boolean isInRange(int n) {
        return n >= getMinReichweite() && n <= getMaxReichweite();
    }

    // Getter für Punktzahl
    public int getPunktzahl() {
        return punktzahl;
    }

    // Erhöht die Punktzahl um 2
    public void incrementScore() {
        punktzahl = punktzahl + 2;
    }
}
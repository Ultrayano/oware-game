package oware.models;

public class Spielfeld {

    private final int ANZAHLGRUBEN = 12;
    private final Spieler spieler1;
    private final Spieler spieler2;
    private final Grube[] gruben = new Grube[ANZAHLGRUBEN];


    /**
     * Konstruktor für das Spielfeld
     *
     * @param spieler1
     * @param spieler2
     */
    public Spielfeld(Spieler spieler1, Spieler spieler2) {
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;

        // erstellt die Spielgruben
        for (int i = 0; i < ANZAHLGRUBEN; i++) {
            gruben[i] = new Grube();
        }
    }

    // Getter-Methode
    public int getGrubenSeeds(int place) {
        return gruben[place].getSeeds();
    }

    /**
     * Schlaufe für:
     * - das Verteilen der Seeds auf die Gruben
     * - Beendet die Schlaufe wenn es keine Seeds mehr zum verteilen hat
     *
     * @param grubenIndex Position der angeklickten Grube
     * @param spielzug    Momentaner Spielzug (Runde in einer Zahl)
     */
    public void moveSeeds(int grubenIndex, int spielzug) {
        int start = grubenIndex + 1;
        for (int seedCount = getGrubenSeeds(grubenIndex); seedCount > 0; seedCount--, start++) {
            if (start >= 12) {
                start = 0;
            }

            // Erhöht die Anzahl Seeds in der jeweiligen Grube (momentaner Index) um 1
            gruben[start].incrementSeed();

            // Schlaufe für das holen von Punkten:
            // - Seeds in der momentanen Grube + die neuen = 2
            // - Startgrube wird auf 0 gesetzt und die Punkte des Spielers der gespielt hat aktualisiert
            if (gruben[start].getSeeds() == 2) {
                if ((spielzug % 2 == 0) && (!spieler1.isInRange(start))) {
                    gruben[start].reset();
                    spieler1.incrementScore();
                } else if ((spielzug % 2 != 0) && (!spieler2.isInRange(start))) {
                    gruben[start].reset();
                    spieler2.incrementScore();
                }
            }

            // Anzahl Seeds auf 0 setzten
            gruben[grubenIndex].reset();
        }
    }
}
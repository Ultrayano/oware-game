package oware;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import oware.models.Grube;
import oware.models.Spieler;
import oware.models.Spielfeld;

import java.util.Random;


public class Main extends Application {

    private final Button[] grubeButtons = new Button[12];
    private final Spieler spieler1 = new Spieler();
    private final Spieler spieler2 = new Spieler();
    private final Spielfeld spielfeld = new Spielfeld(spieler1, spieler2);
    private final Random zufall = new Random();
    private TextField textFeldPunkteSpieler1;
    private TextField textFeldPunkteSpieler2;
    private boolean gewonnen = false;
    // Der Bot ist standardmässig aus
    private boolean gegenComputerSpielen = false;

    // Spieler 1 hat alle geraden Züge und Spieler 2 alle ungenügenden
    private int spielzug = 0;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Startet das Spiel
     * Erstellt zwei Spieler und setzt ihre Reichweite
     * -> Spieler 1 = Grube 1 - 6 und
     * -> Spieler 2 = Grube 7 - 12
     * <p>
     * Erstellt zudem das Spielfeld mit jeweils 6 Gruben pro Seite. (JavaFX Elemente)
     *
     * @param stage ist die Stage in welcher sich das Spiel befindet (Auswahl gegen Spiele oder CPU)
     */
    @Override
    public void start(Stage stage) {
        spieler1.setRange(0, 5);
        spieler2.setRange(6, 11);

        BorderPane root = new BorderPane();
        VBox vbox = new VBox();  // contains all boxes
        VBox vbox1 = new VBox(); // Gruben Player1 + Player2 in VBox
        HBox hboxA = new HBox(); // Gruben Player1
        HBox hboxB = new HBox(); // Gruben Player2

        // Label für Spieler 1
        Label labelSpieler1 = new Label("Punktzahl Spieler 1:");
        labelSpieler1.setPrefSize(175, 30);
        labelSpieler1.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        labelSpieler1.setTextFill(Color.GREEN);

        // Stellt die Punktzahl vom Spieler 1 dar
        textFeldPunkteSpieler1 = new TextField("0");
        textFeldPunkteSpieler1.setDisable(true);
        textFeldPunkteSpieler1.setStyle("-fx-text-inner-color: green");
        textFeldPunkteSpieler1.setPrefSize(35, 30);
        HBox hBoxPunkteSpieler1 = new HBox();
        hBoxPunkteSpieler1.getChildren().addAll(labelSpieler1, textFeldPunkteSpieler1);
        hBoxPunkteSpieler1.setAlignment(Pos.CENTER);
        root.setBottom(hBoxPunkteSpieler1);
        hBoxPunkteSpieler1.setPadding(new Insets(20, 5, 10, 5));


        final Label labelSpieler2 = new Label("Punktzahl Spieler 2: ");
        labelSpieler2.setPrefSize(175, 30);
        labelSpieler2.setFont(Font.font("helvetica", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        // Stellt die Punktzahl vom Spieler 2 dar
        textFeldPunkteSpieler2 = new TextField("0"); // shows Score Player2 --> score displayed in tfScore
        textFeldPunkteSpieler2.setDisable(true);
        textFeldPunkteSpieler2.setPrefSize(35, 30);
        HBox hBoxPunkteSpieler2 = new HBox();
        hBoxPunkteSpieler2.getChildren().addAll(labelSpieler2, textFeldPunkteSpieler2);
        hBoxPunkteSpieler2.setAlignment(Pos.CENTER);
        root.setTop(hBoxPunkteSpieler2);
        hBoxPunkteSpieler2.setPadding(new Insets(10, 5, 20, 5));


        // Grube 7 - 12 Spieler 2 links <-- rechts (rechts nach links)
        grubeButtons[6] = new Button(Integer.toString(spielfeld.getGrubenSeeds(6))); // Pit07
        grubeButtons[7] = new Button(Integer.toString(spielfeld.getGrubenSeeds(7))); // Pit08
        grubeButtons[8] = new Button(Integer.toString(spielfeld.getGrubenSeeds(8))); // Pit09
        grubeButtons[9] = new Button(Integer.toString(spielfeld.getGrubenSeeds(9))); // Pit10
        grubeButtons[10] = new Button(Integer.toString(spielfeld.getGrubenSeeds(10))); // Pit11
        grubeButtons[11] = new Button(Integer.toString(spielfeld.getGrubenSeeds(11))); // Pit12
        hboxB.getChildren().addAll(grubeButtons[11], grubeButtons[10], grubeButtons[9], grubeButtons[8], grubeButtons[7], grubeButtons[6]);
        hboxB.setSpacing(8);
        hboxB.setAlignment(Pos.CENTER);

        // Grube 1 - 6 Spieler 2 links --> rechts (links nach rechts)
        grubeButtons[0] = new Button(Integer.toString(spielfeld.getGrubenSeeds(0))); // Pit1
        grubeButtons[1] = new Button(Integer.toString(spielfeld.getGrubenSeeds(1))); // Pit2
        grubeButtons[2] = new Button(Integer.toString(spielfeld.getGrubenSeeds(2))); // Pit3
        grubeButtons[3] = new Button(Integer.toString(spielfeld.getGrubenSeeds(3))); // Pit4
        grubeButtons[4] = new Button(Integer.toString(spielfeld.getGrubenSeeds(4))); // Pit5
        grubeButtons[5] = new Button(Integer.toString(spielfeld.getGrubenSeeds(5))); // Pit6
        hboxA.getChildren().addAll(grubeButtons[0], grubeButtons[1], grubeButtons[2], grubeButtons[3], grubeButtons[4], grubeButtons[5]);
        hboxA.setSpacing(8);
        hboxA.setAlignment(Pos.CENTER);

        // Design
        for (int i = 0; i < 12; i++) {
            grubeButtons[i].setStyle("-fx-background-radius: 50px; -fx-background-color: #f8f8ff;");
            grubeButtons[i].setPrefSize(90, 90);
            grubeButtons[i].setFont(Font.font("helvetica", FontWeight.BOLD, FontPosture.REGULAR, 20));
        }

        // Beinhaltet alle Gruben
        vbox1.getChildren().addAll(hboxB, hboxA);
        vbox1.setPadding(new Insets(5, 5, 5, 5));
        vbox1.setSpacing(10);
        root.setCenter(vbox1);

        vbox.getChildren().addAll(hBoxPunkteSpieler2, vbox1, hBoxPunkteSpieler1);
        root.setCenter(vbox);
        vbox.setStyle("-fx-background-color: #ffdead;");
        vbox.setAlignment(Pos.CENTER);

        // Spielfeld-Stage
        Scene mainScene = new Scene(root, 700, 350);

        Button button1v1 = new Button("Normal - 1 vs 1");
        button1v1.setPrefSize(420, 75);
        button1v1.setStyle("-fx-background-color: #f8f8ff;");

        Button buttonCPU = new Button("gegen den super-quantum-computer");
        buttonCPU.fontProperty();
        buttonCPU.setStyle("-fx-background-color: f8f8ff;");
        buttonCPU.setPrefSize(420, 75);

        VBox vboxFirstPage = new VBox(button1v1, buttonCPU);
        vboxFirstPage.setStyle("-fx-background-color: #ffdead;");


        // Auswahlstage
        Scene startScene = new Scene(vboxFirstPage, 700, 350);
        vboxFirstPage.setPadding(new Insets(25, 15, 25, 15));
        vboxFirstPage.setSpacing(12);
        vboxFirstPage.setAlignment(Pos.CENTER);


        // Erste Seite zum wählen ob man gegen einen CPU oder gegen einen anderen Spieler spielen will
        stage.setTitle("Oware-Game fresh out of Africa");
        stage.setScene(startScene);
        stage.show();


        // Setzt Mainstage (Spielfeld) wenn etwas gedrückt wird
        button1v1.setOnAction(event -> stage.setScene(mainScene));

        buttonCPU.setOnAction(event -> {
            stage.setScene(mainScene);
            gegenComputerSpielen = true;
        });

        /**
         * Globaler EventHandler, welcher auf Buttonklicks hört und die Seeds in den {@link Grube} bewegt
         */
        root.addEventHandler(ActionEvent.ANY, event -> {

            for (int i = 0; i < grubeButtons.length; i++) {

                // Klick auf Button -> Überprüft welcher Spielzug dran ist und erhöht den Spielzug (Runde) um 1
                // Aktualisiert danach das Spielfeld -> Bewegt Seeds von der Startgrube in die nächsten Gruben
                // Wenn der Spieler eine Grube anklickt, die nicht ihm gehört, dann wird ein Alert ausgeworfen
                if (grubeButtons[i] == event.getTarget()) {

                    if ((spielzug % 2 == 0) && (spieler1.isInRange(i))) {
                        if (spielfeld.getGrubenSeeds(i) != 0 || playerCantPlay(spieler1)) {
                            spielzug += 1;
                            updatePlayfield(i, spielzug - 1);

                            // Wenn der momentane Spieler keine Grube mehr auf seiner Seite hat, wird der Spielzug (Runde) um 1 erhöht, damit der nächste Spieler dran ist
                            if (playerCantPlay(spieler2)) {
                                spielzug += 1;
                            } else {
                                labelSpieler2.setTextFill(Color.GREEN);
                                textFeldPunkteSpieler2.setStyle("-fx-text-inner-color: green");

                                labelSpieler1.setTextFill(Color.BLACK);
                                textFeldPunkteSpieler1.setStyle("-fx-text-inner-color: black");
                            }

                            if ((gegenComputerSpielen) && (!gewonnen) && (spielzug % 2 != 0)) {
                                // Generiert zufälliger Spielzug, der der Computer macht
                                int minRange = 6, maxRange = 12;
                                int randNum;

                                do {
                                    do {
                                        randNum = zufall.nextInt(maxRange - minRange) + minRange;
                                    } while (((spielfeld.getGrubenSeeds(randNum) == 0) && (!playerCantPlay(spieler2))));
                                    grubeButtons[randNum].fire();
                                } while (playerCantPlay(spieler1));
                            }
                        }

                    } else if ((spielzug % 2 != 0) && (spieler2.isInRange(i))) {
                        if (spielfeld.getGrubenSeeds(i) != 0 || playerCantPlay(spieler2)) {
                            spielzug += 1;
                            updatePlayfield(i, spielzug - 1);

                            // Wenn der momentane Spieler keine Grube mehr auf seiner Seite hat, wird der Spielzug (Runde) um 1 erhöht, damit der nächste Spieler dran ist
                            if (playerCantPlay(spieler1)) {
                                spielzug += 1;
                            } else {
                                labelSpieler2.setTextFill(Color.BLACK);
                                textFeldPunkteSpieler2.setStyle("-fx-text-inner-color: black");

                                labelSpieler1.setTextFill(Color.GREEN);
                                textFeldPunkteSpieler1.setStyle("-fx-text-inner-color: green");
                            }
                        }

                    } else {
                        alertWrongRange();
                    }
                }
            }
        });
    }

    // Testet ob der Spieler noch Seeds auf seiner Seite hat (ob er spielen kann)
    private boolean playerCantPlay(Spieler spieler) {
        int maxRange = spieler.getMaxReichweite() + 1;
        int seeds = 0;
        for (int range = spieler.getMinReichweite(); range < maxRange; range++) {
            seeds += spielfeld.getGrubenSeeds(range);
        }
        return seeds == 0;
    }

    // Aktualisiert das Spielfeld
    private void updatePlayfield(int pitIndex, int turn) {
        spielfeld.moveSeeds(pitIndex, turn);
        for (int i = 0; i < grubeButtons.length; i++) {
            grubeButtons[i].setText(String.valueOf(spielfeld.getGrubenSeeds(i)));
            grubeButtons[i].setDisable(spielfeld.getGrubenSeeds(i) == 0);
            textFeldPunkteSpieler1.setText(String.valueOf(spieler1.getPunktzahl()));
            textFeldPunkteSpieler2.setText(String.valueOf(spieler2.getPunktzahl()));

            // Wenn die Punktzahl von beiden Spielern addiert 46 ist, ist das Spiel vorbei
            if (spieler1.getPunktzahl() + spieler2.getPunktzahl() == 46) {
                win();
                this.gewonnen = true;
            }
        }
    }

    /**
     * Methode um das Spiel zu beenden
     */
    private void win() {
        String winText;

        // Überprüft wer gewonnen hat
        if (spieler1.getPunktzahl() > spieler2.getPunktzahl()) {
            winText = "Gratulation Spieler 1! Du hast gewonnen!";
        } else if ((gegenComputerSpielen) && spieler1.getPunktzahl() < spieler2.getPunktzahl()) {
            winText = "Der super-quantum-computer hat das Spiel gewonnen!";
        } else if (spieler1.getPunktzahl() < spieler2.getPunktzahl()) {
            winText = "Gratulation Spieler 2! Du hast gewonnen!";
        } else {
            winText = "Niemand hat gewonnen! Gleichstand!";
        }

        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
        winAlert.setTitle("Ende des Spiel");
        winAlert.setHeaderText(winText);
        winAlert.setContentText("\nDanke für's spielen!");

        winAlert.showAndWait();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Methode um den Spieler darauf hinzuweisen, dass die angeklickte Grube nicht zu seiner Spielseite gehört
     */
    private void alertWrongRange() {
        Alert alertWrongRange = new Alert(Alert.AlertType.WARNING);
        alertWrongRange.setHeaderText("Nicht deine Grube!");
        alertWrongRange.show();
        alertWrongRange.setContentText("Diese Grube gehört nicht zu deiner Seite. Wähle eine andere! ");
    }
}

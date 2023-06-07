package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.mecanique.Jeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.scene.effect.DropShadow;


import java.io.IOException;
import java.util.Objects;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Pane  {

    private final IDestination destination;
    private Button button;
    private VBox vbox;


    public VueDestination(IDestination destination) throws IOException {

        this.destination = destination;
        vbox = new VBox();
        Label ville1 = new Label(destination.getVilles().get(0));
        ville1.setFont(Font.font("Cabin", FontWeight.MEDIUM, 15));
        Label ville2 = new Label(destination.getVilles().get(1));
        ville2.setFont(Font.font("Cabin", FontWeight.MEDIUM, 15));
        Label numCarte = new Label(String.valueOf(destination.getValeur()));
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(ville1, ville2, numCarte);



        vbox.setPrefHeight(70);
        vbox.setPrefWidth(120);


        DropShadow ds = new DropShadow();
        ds.setRadius(10);
        ds.setColor(Color.BLACK);
        ds.setOffsetX(3);
        ds.setOffsetY(3);
        vbox.setEffect(ds);
        vbox.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 5px;");


        vbox.setOnMouseEntered(e -> {
            vbox.setStyle("-fx-background-color: #FFE6C7");
        });
        vbox.setOnMouseExited(e -> {
            vbox.setStyle("-fx-background-color: #F6E7D4");
        });

        this.getChildren().add(vbox);




    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VueDestination that = (VueDestination) o;
        return Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination);
    }

    public IDestination getDestination() {
        return destination;
    }

}

package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.RailsIHM;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.util.List;

/**
 * Cette classe affiche les scores en fin de partie.
 * On peut éventuellement proposer de rejouer, et donc de revenir à la fenêtre principale
 *
 */
public class VueResultats extends GridPane {

    private RailsIHM ihm;

    private final ObservableList<String> nomsJoueurs;



    HBox joueurBox;

    ImageView j1img;
    TextField j1tf;

    ImageView j2img;
    TextField j2tf;
    VBox j3Box;
    ImageView j3img;
    TextField j3tf;

    ImageView j4img;
    TextField j4tf;
    ImageView j5img;
    TextField j5tf;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }



    public VueResultats(RailsIHM ihm) {
        this.ihm = ihm;
        nomsJoueurs = FXCollections.observableArrayList();
        this.joueurBox = new HBox();

        this.setPrefWidth(1600);
        this.setPrefHeight(900);
        this.setStyle("-fx-background-color: #C8AD7F");


        VBox j1Box = new VBox();
        j1img = new ImageView(new Image("images/cartesWagons/avatar-BLEU.png"));
        j1img.setFitWidth(150);
        j1img.setFitHeight(175);
        j1img.setTranslateX(22.5);
        j1tf = new TextField();
        j1tf.setTranslateY(10);
        j1tf.setPromptText("Insérez un nom pour le Joueur 1");
        j1tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j1tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j1Box.getChildren().addAll(j1img, j1tf);
        j1Box.setTranslateX(5);

        VBox j2Box = new VBox();
        j2img = new ImageView(new Image("images/cartesWagons/avatar-JAUNE.png"));
        j2img.setFitWidth(150);
        j2img.setFitHeight(175);
        j2img.setTranslateX(22.5);
        j2tf = new TextField();
        j2tf.setTranslateY(10);
        j2tf.setPromptText("Insérez un nom pour le Joueur 2");
        j2tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j2tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j2Box.getChildren().addAll(j2img, j2tf);
        j2Box.setTranslateX(5);

        VBox j3Box = new VBox();
        j3img = new ImageView(new Image("images/cartesWagons/avatar-ROSE.png"));
        j3img.setFitWidth(150);
        j3img.setFitHeight(175);
        j3img.setTranslateX(22.5);
        j3tf = new TextField();
        j3tf.setTranslateY(10);
        j3tf.setPromptText("Insérez un nom pour le Joueur 3");
        j3tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j3tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j3Box.getChildren().addAll(j3img, j3tf);
        j3Box.setTranslateX(5);

        VBox j4Box = new VBox();
        j4img = new ImageView(new Image("images/cartesWagons/avatar-ROUGE.png"));
        j4img.setFitWidth(150);
        j4img.setFitHeight(175);
        j4img.setTranslateX(22.5);
        j4tf = new TextField();
        j4tf.setTranslateY(10);
        j4tf.setPromptText("Insérez un nom pour le Joueur 4");
        j4tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j4tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j4Box.getChildren().addAll(j4img, j4tf);
        j4Box.setTranslateX(5);

        VBox j5Box = new VBox();
        j5img = new ImageView(new Image("images/cartesWagons/avatar-VERT.png"));
        j5img.setFitWidth(150);
        j5img.setFitHeight(175);
        j1img.setTranslateX(22.5);
        j5tf = new TextField();
        j5tf.setTranslateY(10);
        j5tf.setPromptText("Insérez un nom pour le Joueur 5");
        j5tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j5tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j5Box.getChildren().addAll(j5img, j5tf);
        j5Box.setTranslateX(5);

        joueurBox.getChildren().addAll(j1Box,j2Box);
        joueurBox.setSpacing(10.0);
        joueurBox.setAlignment(Pos.CENTER);
        joueurBox.setTranslateY(150);

        if(ihm.getJeu().getJoueurs().size() == 2){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
        }else if(ihm.getJeu().getJoueurs().size() == 3){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
        }else if(ihm.getJeu().getJoueurs().size() == 4){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
            this.nomsJoueurs.add(3, this.j4tf.getText());
        }else if(ihm.getJeu().getJoueurs().size() == 5){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
            this.nomsJoueurs.add(3, this.j4tf.getText());
            this.nomsJoueurs.add(4, this.j5tf.getText());
        }

        this.getChildren().add(joueurBox);
    }

}

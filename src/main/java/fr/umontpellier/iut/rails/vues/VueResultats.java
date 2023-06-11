package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.RailsIHM;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.text.StyledEditorKit;
import java.util.List;

/**
 * Cette classe affiche les scores en fin de partie.
 * On peut éventuellement proposer de rejouer, et donc de revenir à la fenêtre principale
 *
 */
public class VueResultats extends GridPane  {

    private RailsIHM ihm;
    HBox joueurBox;

    ImageView j1img;
    Label j1score;
    ImageView j2img;

    Label j2score;
    ImageView j3img;

    Label j3score;

    ImageView j4img;

    Label j4score;
    ImageView j5img;

    Label j5score;

    Label titre;





    public VueResultats(RailsIHM ihm) {

        this.ihm = ihm;

        this.setPrefWidth(1600);
        this.setPrefHeight(900);
        this.setStyle("-fx-background-color: #C8AD7F");

        VBox j1Box = new VBox();
        j1img = new ImageView(new Image("images/cartesWagons/avatar-BLEU.png"));
        j1img.setFitWidth(150);
        j1img.setFitHeight(175);

        j1score = new Label();
        j1score.setFont(Font.font("Verdana", FontPosture.REGULAR, 75));

        j1score.setText(String.valueOf(ihm.getJeu().getJoueurs().get(0).getScore()));
        j1Box.getChildren().addAll(j1img,j1score);


        VBox j2Box = new VBox();
        j2img = new ImageView(new Image("images/cartesWagons/avatar-JAUNE.png"));
        j2img.setFitWidth(150);
        j2img.setFitHeight(175);

        j2score = new Label();
        j2score.setFont(Font.font("Verdana", FontPosture.REGULAR, 75));

        j2score.setText(String.valueOf(ihm.getJeu().getJoueurs().get(1).getScore()));
        j2Box.getChildren().addAll(j2img,j2score);


        VBox j3Box = new VBox();
        j3img = new ImageView(new Image("images/cartesWagons/avatar-ROSE.png"));
        j3img.setFitWidth(150);
        j3img.setFitHeight(175);

        j3score = new Label();
        j3score.setFont(Font.font("Verdana", FontPosture.REGULAR, 75));
        j3score.setAlignment(Pos.CENTER);




        j3Box.getChildren().addAll(j3img,j3score);


        VBox j4Box = new VBox();
        j4img = new ImageView(new Image("images/cartesWagons/avatar-ROUGE.png"));
        j4img.setFitWidth(150);
        j4img.setFitHeight(175);

        j4score = new Label();
        j4score.setFont(Font.font("Verdana", FontPosture.REGULAR, 75));
        j4score.setAlignment(Pos.CENTER);




        j4Box.getChildren().addAll(j4img,j4score);



        VBox j5Box = new VBox();
        j5Box.setPrefSize(200,200);
        j5img = new ImageView(new Image("images/cartesWagons/avatar-VERT.png"));
        j5img.setFitWidth(150);
        j5img.setFitHeight(175);

        j5score = new Label();
        j5score.setFont(Font.font("Verdana", FontPosture.REGULAR, 75));
        j5score.setAlignment(Pos.CENTER);

        j1Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j2Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j3Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j4Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j5Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        j5Box.getChildren().addAll(j5img,j5score);


        joueurBox = new HBox();
        joueurBox.setAlignment(Pos.CENTER);

        if(ihm.getJeu().getJoueurs().size()==2){
            joueurBox.getChildren().addAll(j1Box,j2Box);
            j1score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(0).getScore()));
            j1score.setAlignment(Pos.CENTER);
            j2score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(1).getScore()));
            j2score.setAlignment(Pos.CENTER);

        }
        else if(ihm.getJeu().getJoueurs().size()==3){
            joueurBox = new HBox(j1Box,j2Box,j3Box);
            j1score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(0).getScore()));
            j1score.setAlignment(Pos.CENTER);
            j2score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(1).getScore()));
            j2score.setAlignment(Pos.CENTER);
            j3score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(2).getScore()));
            j3score.setAlignment(Pos.CENTER);


        }
        else if(ihm.getJeu().getJoueurs().size()==4){
            joueurBox = new HBox(j1Box,j2Box,j3Box,j4Box);
            j1score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(0).getScore()));
            j1score.setAlignment(Pos.CENTER);
            j2score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(1).getScore()));
            j2score.setAlignment(Pos.CENTER);
            j3score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(2).getScore()));
            j3score.setAlignment(Pos.CENTER);
            j4score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(3).getScore()));
            j4score.setAlignment(Pos.CENTER);
        }
        else if(ihm.getJeu().getJoueurs().size()==5){
            joueurBox = new HBox(j1Box,j2Box,j3Box,j4Box,j5Box);
            j1score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(0).getScore()));
            j1score.setAlignment(Pos.CENTER);
            j2score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(1).getScore()));
            j2score.setAlignment(Pos.CENTER);
            j3score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(2).getScore()));
            j3score.setAlignment(Pos.CENTER);
            j4score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(3).getScore()));
            j4score.setAlignment(Pos.CENTER);
            j5score.setText(String.valueOf(this.ihm.getJeu().getJoueurs().get(4).getScore()));
            j5score.setAlignment(Pos.CENTER);
        }


        joueurBox.setSpacing(50);
        joueurBox.setAlignment(Pos.CENTER);

        Button rejouer = new Button("Rejouer ?");
        rejouer.setPrefHeight(50);
        rejouer.setPrefWidth(350);
        rejouer.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        rejouer.setFont(Font.font("Cabin", FontWeight.MEDIUM, 22));
        rejouer.setOnMouseEntered(e -> {
            rejouer.setStyle("-fx-background-color: #FFE6C7; -fx-background-radius: 25px");
        });
        rejouer.setOnMouseExited(e -> {
            rejouer.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        });
        rejouer.setAlignment(Pos.CENTER);
        DropShadow ds = new DropShadow();
        ds.setRadius(10);
        ds.setColor(Color.BLACK);
        ds.setOffsetX(3);
        ds.setOffsetY(3);
        rejouer.setEffect(ds);

        rejouer.setOnAction(e -> {
            ihm.demarrerPartie();
        });

        HBox hboxRejouer = new HBox();
        hboxRejouer.getChildren().add(rejouer);
        hboxRejouer.setAlignment(Pos.CENTER);
        hboxRejouer.setSpacing(50);
        hboxRejouer.setTranslateY(50);


        this.addRow(0,joueurBox);
        this.setPadding(new Insets(50,50,50,50));

        this.addRow(1,hboxRejouer);



        this.setAlignment(Pos.CENTER);



    }

}

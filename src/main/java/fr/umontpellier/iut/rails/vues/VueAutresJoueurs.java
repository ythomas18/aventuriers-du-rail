package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {

        List<VueJoueur> joueurs;
        IJeu jeu;
        private HBox titleBox;




        public VueAutresJoueurs(IJeu jeu) {
                this.jeu = jeu;
                this.joueurs = new ArrayList<>();
                for (IJoueur j : jeu.getJoueurs()) {
                        IJoueur joueur = j;
                        VueJoueur vue = new VueJoueur(joueur);
                        this.joueurs.add(vue);
                }
                for (VueJoueur v : joueurs) {
                        getChildren().add(v);
                }
                jeu.joueurCourantProperty().addListener((observable, oldValue, newValue) -> {
                        for(VueJoueur v : joueurs){
                                if(v.getJoueur().equals(oldValue)){
                                        v.getImageJoueur().setFitHeight(80);
                                        v.nomJoueur.setStyle("-fx-font-size: 0px; -fx-font-weight: normal ;");

                                }
                                if(v.getJoueur().equals(newValue)){
                                        v.getImageJoueur().setFitHeight(90);
                                        v.nomJoueur.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
                                }

                        }
                });
                setAlignment(Pos.CENTER);

                for(VueJoueur v : joueurs){

                        v.setBorder(new Border(new BorderStroke(Color.BLACK,
                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }


        }

        static class VueJoueur extends HBox{
                IJoueur joueur;

                Label nomJoueur;

                VBox infosJoueur;

                HBox imageXpseudo;

                VBox pionsJoueur;

                HBox pionsWagon;

                HBox pionsBateau;

                HBox ports;

                HBox hboxscore;

                String couleur;
                ImageView imageJoueur;

                private FadeTransition fadeTransition;


                VueJoueur(IJoueur joueur){
                        this.joueur = joueur;
                        this.infosJoueur = new VBox();
                        this.imageXpseudo = new HBox();
                        this.pionsWagon = new HBox();
                        this.pionsBateau = new HBox();
                        this.hboxscore = new HBox();
                        this.pionsJoueur = new VBox();
                        this.ports = new HBox();
                        this.nomJoueur = new Label(joueur.getNom());

                        String couleur = CouleurJoueur(joueur);
                        imageJoueur = new ImageView("/images/cartesWagons/avatar-"+joueur.getCouleur().toString()+".png");
                        imageJoueur.setFitHeight(80);
                        imageJoueur.preserveRatioProperty().set(true);

                        ImageView logoPionWagon = new ImageView("/images/bouton-pions-wagon.png");
                        logoPionWagon.setPreserveRatio(true);
                        logoPionWagon.setFitHeight(20);

                        ImageView logoPionBateau = new ImageView("/images/bouton-pions-bateau.png");
                        logoPionBateau.setPreserveRatio(true);
                        logoPionBateau.setFitHeight(20);

                        ImageView logoPort= new ImageView("/images/port.png");
                        logoPort.setPreserveRatio(true);
                        logoPort.setFitHeight(20);

                        Label scoredeuxpoints = new Label("Score : ");



                        Label nbPionsWagonLabel = new Label();
                        nbPionsWagonLabel.textProperty().bind(joueur.nbPionsWagonsProperty().asString());
                        nbPionsWagonLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                        Label nbPionsBateauLabel = new Label();
                        nbPionsBateauLabel.textProperty().bind(joueur.nbPionsBateauxProperty().asString());
                        nbPionsBateauLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                        Label nbPortLabel= new Label();
                        nbPortLabel.textProperty().bind(joueur.nbPortsProperty().asString());
                        nbPortLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                        Label scoreJoueur = new Label();
                        scoreJoueur.textProperty().bind(joueur.scoreProperty().asString());
                        scoreJoueur.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

                        pionsWagon.getChildren().addAll(logoPionWagon,nbPionsWagonLabel);
                        pionsBateau.getChildren().addAll(logoPionBateau,nbPionsBateauLabel);
                        ports.getChildren().addAll(logoPort,nbPortLabel);
                        hboxscore.getChildren().addAll(scoredeuxpoints,scoreJoueur);

                        pionsJoueur.getChildren().addAll(pionsWagon,pionsBateau,ports,hboxscore);
                        imageXpseudo.getChildren().addAll(imageJoueur,nomJoueur);

                        pionsJoueur.setVisible(false);



                        imageXpseudo.setOnMouseEntered(event -> showPawnsVBox());
                        imageXpseudo.setOnMouseExited(event -> hidePawnsVBox());

                        infosJoueur.getChildren().addAll(imageXpseudo, pionsJoueur);



                        this.getChildren().add(infosJoueur);
                        this.setStyle(couleur);
                        this.getImageJoueur().setFitHeight(100);

                        fadeTransition = new FadeTransition(Duration.millis(100), pionsJoueur);
                        fadeTransition.setFromValue(0.0);  // Transparence initiale
                        fadeTransition.setToValue(1.0);    // Transparence complète
                }

                private void showPawnsVBox() {
                        fadeTransition.play();
                        pionsJoueur.setVisible(true);
                }

                private void hidePawnsVBox() {
                        pionsJoueur.setVisible(false);
                        fadeTransition.play();
                }

                String CouleurJoueur(IJoueur joueur){
                        IJoueur.CouleurJoueur couleurJoueur= joueur.getCouleur();
                        switch (couleurJoueur){
                                case JAUNE:
                                        return "-fx-background-color: #FFD700;";
                                case ROUGE:
                                        return "-fx-background-color: #f51832;";
                                case BLEU:
                                        return "-fx-background-color: #87CEFA;";
                                case VERT:
                                        return "-fx-background-color: #6B8E23;";
                                case ROSE:
                                        return "-fx-background-color: #ffb9c7;";
                                default:
                                        return "-fx-background-color: #000000;";
                        }
                }



                public ImageView getImageJoueur(){
                        return this.imageJoueur;
                }
                public IJoueur getJoueur() {
                        return this.joueur;
                }



        }







}

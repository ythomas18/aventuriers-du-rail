package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
                setSpacing(5);
                for(VueJoueur v : joueurs){
                        v.setPadding(new Insets(5));
                }


        }

        static class VueJoueur extends HBox{
                IJoueur joueur;

                Label nomJoueur;

                String couleur;
                ImageView imageJoueur;

                VueJoueur(IJoueur joueur){
                        this.joueur = joueur;
                        this.nomJoueur = new Label(joueur.getNom());
                        String couleur = CouleurJoueur(joueur);
                        imageJoueur = new ImageView("/images/cartesWagons/avatar-"+joueur.getCouleur().toString()+".png");
                        imageJoueur.setFitHeight(30);
                        imageJoueur.preserveRatioProperty().set(true);
                        this.getChildren().addAll(imageJoueur,nomJoueur);
                        this.setStyle(couleur);
                        this.getImageJoueur().setFitHeight(100);
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

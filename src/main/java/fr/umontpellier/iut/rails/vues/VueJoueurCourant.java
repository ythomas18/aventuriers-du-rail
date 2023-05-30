package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;

    private VBox CarteJoueurCourant;

    private GridPane rangementCartes;


    public VueJoueurCourant(String nom_du_joueur) {
        this.nomJoueur = new Label();
        this.CarteJoueurCourant= new VBox();
        CarteJoueurCourant.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll( nomJoueur, CarteJoueurCourant);
        CarteJoueurCourant.setSpacing(5);

    }

    public void creerBindings(IJeu jeu) {
        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                this.supprimerCarte()
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nomJoueur.setText(nouveauJoueur.getNom())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                afficheCarte(nouveauJoueur.getCartesTransport())
        );







    }

    public void afficheCarte(List<? extends ICarteTransport> cartes){
        for (ICarteTransport carteTransport: cartes){
            ImageView lbCarte = new ImageView("/images/cartesWagons/" + getImagePourCarte(carteTransport));
            lbCarte.setPreserveRatio(true);
            lbCarte.setFitHeight(65);

            CarteJoueurCourant.getChildren().add(lbCarte);


        }

    }

    public void supprimerCarte(){
        CarteJoueurCourant.getChildren().clear();
    }



    public String getImagePourCarte(ICarteTransport carteTransport){
        String str = "carte-";
        if(carteTransport.estDouble()){
            str += "DOUBLE";
        }

        else if(carteTransport.estBateau()){
            str += "BATEAU";
        }

        else if(carteTransport.estJoker()){
            str += "JOKER";
        }
        else if(carteTransport.estWagon()){
            str += "WAGON";
        }

        str = str + "-" + carteTransport.getStringCouleur();

        if(carteTransport.getAncre()){
            str += "-A";
        }

        return str+".png";

    }

}

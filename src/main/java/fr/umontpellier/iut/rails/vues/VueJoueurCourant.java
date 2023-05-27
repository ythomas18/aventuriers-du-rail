package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import javafx.scene.control.Label;
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

    public VueJoueurCourant(String nom_du_joueur) {
        this.nomJoueur = new Label();
        this.CarteJoueurCourant= new VBox();
        CarteJoueurCourant.getChildren().addAll();
        this.getChildren().addAll(this.nomJoueur,CarteJoueurCourant);

    }

    public void creerBindings(IJeu jeu) {
        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
        nomJoueur.setText(nouveauJoueur.getNom()));
        supprimerCarte();




    }

    public void afficheCarte(List<ICarteTransport> cartes){
        for (ICarteTransport carteTransport: cartes){
            Label lbCarte= new Label(carteTransport.toString());
            CarteJoueurCourant.getChildren().addAll(lbCarte);
        }
    }

    public void supprimerCarte(){
        CarteJoueurCourant.getChildren().clear();
    }

}

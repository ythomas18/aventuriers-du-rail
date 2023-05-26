package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les cartes Transport visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private final IJeu jeu;
    private VuePlateau plateau;

    private VBox listeDestination;



    private final ListChangeListener<IDestination> toto = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (IDestination iDestination : change.getAddedSubList()) {
                    listeDestination.getChildren().add(new Label(iDestination.getVilles().toString()));
                }
            } else if (change.wasRemoved()) {
                for (IDestination iDestination : change.getRemoved()) {
                    listeDestination.getChildren().remove(removeDestination(iDestination));
                }
            }
        }






    };

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        //getChildren().add(plateau);
        Label labelDestinationInitiale = new Label();
        Button btnPasser = new Button("Passer");

        Label lblInstructions = new Label();

        lblInstructions.textProperty().bind(jeu.instructionProperty());


        this.listeDestination = new VBox();

        this.getChildren().addAll(lblInstructions, btnPasser,listeDestination);

        EventHandler<MouseEvent> btnPasserHandlerClick = MouseEvent -> {
            jeu.passerAEteChoisi();
        };

        btnPasser.setOnMouseClicked(btnPasserHandlerClick);



        jeu.destinationsInitialesProperty().addListener(toto);

        VueJoueurCourant vueJoueurCourant= new VueJoueurCourant("Nom du joueur");
        plateau.getChildren().addAll(vueJoueurCourant);
    }

    public Label removeDestination(IDestination destination){
        for(Node n : listeDestination.getChildren()){
            Label labelDestination = (Label) n;
            if(labelDestination.getText().equals(destination.getVilles().toString())){
                return labelDestination;
            }
        }
        return null;

    }

    public void creerBindings() {
        plateau.prefWidthProperty().bind(getScene().widthProperty());
        plateau.prefHeightProperty().bind(getScene().heightProperty());
        plateau.creerBindings();


    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> getJeu().passerAEteChoisi());


}

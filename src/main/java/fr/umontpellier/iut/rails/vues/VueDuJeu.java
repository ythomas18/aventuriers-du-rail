package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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

    private HBox listeDestination;



    private final ListChangeListener<IDestination> toto = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (IDestination iDestination : change.getAddedSubList()) {
                    try {
                        listeDestination.getChildren().add(new VueDestination(iDestination));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else if (change.wasRemoved()) {
                for (IDestination iDestination : change.getRemoved()) {
                    try {
                        listeDestination.getChildren().remove(removeDestination(iDestination));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        HBox plat= new HBox();
        this.setStyle("-fx-background-color: #C8AD7F");


        plateau.setMaxSize(1000,1000);
        plat.setMaxSize(3000, 3000);
        VueJoueurCourant vueJoueurCourant= new VueJoueurCourant("Nom joueur");
        vueJoueurCourant.creerBindings(jeu);


        plat.getChildren().addAll(plateau, vueJoueurCourant);
        this.getChildren().add(plat);

        Label labelDestinationInitiale = new Label();
        Button btnPasser = new Button("Passer");


        Label lblInstructions = new Label();

        lblInstructions.textProperty().bind(jeu.instructionProperty());

        this.listeDestination = new HBox();
        listeDestination.setSpacing(40);

        //this.getChildren().addAll(lblInstructions, btnPasser,listeDestination);

        EventHandler<MouseEvent> btnPasserHandlerClick = MouseEvent -> {
            jeu.passerAEteChoisi();
        };

        btnPasser.setOnMouseClicked(btnPasserHandlerClick);

        jeu.destinationsInitialesProperty().addListener(toto);


        HBox bas= new HBox();
        VBox vBox= new VBox();
        bas.getChildren().add(btnPasser);
        vBox.getChildren().addAll(bas, lblInstructions, listeDestination);
        vBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        vBox.setMinSize(300,220);
        vBox.setMaxSize(600,420);
        this.getChildren().addAll(vBox);








    }

    public VueDestination removeDestination(IDestination destination) throws IOException {
        for(Node n : listeDestination.getChildren()){
            VueDestination destination1 = (VueDestination) n;
            if(destination1.equals(new VueDestination(destination))){
                return (VueDestination) n;
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

package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.mecanique.Jeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.io.IOException;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends Pane  {

    private final IDestination destination;

    @FXML
    private Label ville1;

    @FXML
    private Label ville2;

    @FXML
    private Label idCarte;





    public VueDestination(IDestination destination) {
        this.destination = destination;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/carteDestination.fxml"));
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ville1 = new Label();
        ville2 = new Label();
        idCarte = new Label();

        ville1.setText(this.destination.getVilles().get(0));
        ville2.setText(this.destination.getVilles().get(1));
        idCarte.setText(String.valueOf(this.destination.getValeur()));




    }



    public IDestination getDestination() {
        return destination;
    }

}

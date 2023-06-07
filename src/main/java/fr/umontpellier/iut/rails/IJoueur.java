package fr.umontpellier.iut.rails;

import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface IJoueur {

    ObservableList<? extends ICarteTransport> cartesTransportProperty();
    ObservableList<? extends CarteTransport> cartesTransportPoseesProperty();
    IntegerProperty nbPionsWagonsProperty();
    IntegerProperty nbPionsBateauxProperty();
    IntegerProperty nbPionsWagonsEnReserveProperty();
    IntegerProperty nbPionsBateauxEnReserveProperty();


    IntegerProperty nbPortsProperty();
    IntegerProperty scoreProperty();



    List<? extends ICarteTransport> getCartesTransport();
    List<? extends IDestination> getDestinations();
    int getNbPionsWagon();
    int getNbPionsBateau();
    int getNbPorts();
    String getNom();
    CouleurJoueur getCouleur();
    int getScore();

    enum CouleurJoueur {
        JAUNE, ROUGE, BLEU, VERT, ROSE
    }




}
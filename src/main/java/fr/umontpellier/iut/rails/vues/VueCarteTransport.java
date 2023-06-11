package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.List;

/**
 * Cette classe représente la vue d'une carte Transport.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteTransport extends Pane {

    private final ICarteTransport carteTransport;

    private List<ICarteTransport> listeCartesTransport;

    private EventHandler<MouseEvent> carteEstJouee = event -> {
        IJeu jeu = ((VueDuJeu) getScene().getRoot()).getJeu();
        VueCarteTransport source = (VueCarteTransport) event.getSource();
        jeu.uneCarteDuJoueurEstJouee(source.getCarteTransport());
    };




    public VueCarteTransport(ICarteTransport carteTransport, int nbCartes) {
        this.carteTransport = carteTransport;
        Circle cercle = new Circle();
        cercle.setRadius(10);
        cercle.setFill(Color.web("#4b4b4b"));
        Label value = new Label();
        value.setText(nbCartes+"");
        //int nbCartesIdentiques = getNombreCarteTransportIdentiques(nbCartes);
        //value.setText(Integer.toString(nbCartesIdentiques));
        value.textFillProperty().set(Paint.valueOf("white"));
        StackPane stack = new StackPane();
        stack.getChildren().addAll(cercle, value);
        ImageView imageCarte = new ImageView("images/cartesWagons/"+getImagePourCarte(carteTransport));
        imageCarte.setPreserveRatio(true);
        imageCarte.setFitHeight(65);



        Pane carte = new Pane(imageCarte);
        carte.getChildren().add(stack);

        this.setOnMouseClicked(carteEstJouee);

        this.getChildren().add(carte);
    }

    /*public int getNombreCarteTransportIdentiques(int nbCartes){
        //int nbCartes= 0;
        for (ICarteTransport carte: listeCartesTransport){
            if (carte.equals(this.carteTransport)){
                nbCartes++;
            }
        }
        return nbCartes;
    }*/



    public ICarteTransport getCarteTransport() {
        return carteTransport;
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

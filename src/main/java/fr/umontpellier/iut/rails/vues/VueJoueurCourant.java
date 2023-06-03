package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;

    private HBox CarteJoueurCourant;

    private GridPane rangementCartes;

    private HBox carteJoueurCourantLigne1;

    private HBox carteJoueurCourantLigne2;


    public VueJoueurCourant(String nom_du_joueur) {

        this.nomJoueur = new Label();
        this.CarteJoueurCourant= new HBox();
        CarteJoueurCourant.setAlignment(Pos.CENTER_RIGHT);
        CarteJoueurCourant.setSpacing(5);

        carteJoueurCourantLigne1 = new HBox();
        carteJoueurCourantLigne2 = new HBox();

        VBox contenue = new VBox(carteJoueurCourantLigne1, carteJoueurCourantLigne2);
        contenue.setAlignment(Pos.CENTER_RIGHT);
        contenue.setSpacing(5);
        contenue.setMaxSize(100, 200);

        this.getChildren().addAll(nomJoueur, contenue);
        CarteJoueurCourant.getChildren().add(this);





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
        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) -> {
            Color couleurJoueur = convertirCouleur(nouveauJoueur.getCouleur());
            String couleurHex = convertirEnHexadecimal(couleurJoueur);
            carteJoueurCourantLigne1.setStyle("-fx-background-color: " + couleurHex + ";");
            carteJoueurCourantLigne2.setStyle("-fx-background-color: " + couleurHex + ";");
        });

    }

    public void afficheCarte(List<? extends ICarteTransport> cartes){
        carteJoueurCourantLigne1.getChildren().clear();
        carteJoueurCourantLigne2.getChildren().clear();

        for (int i = 0; i < cartes.size(); i++) {
            ICarteTransport carteTransport = cartes.get(i);
            ImageView lbCarte = new ImageView("/images/cartesWagons/" + getImagePourCarte(carteTransport));
            lbCarte.setPreserveRatio(true);
            lbCarte.setFitHeight(65);


            if (i < 5) {
                carteJoueurCourantLigne1.getChildren().add(lbCarte);
            } else {
                carteJoueurCourantLigne2.getChildren().add(lbCarte);
            }
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

    private Color convertirCouleur(IJoueur.CouleurJoueur couleur) {
        switch (couleur) {
            case JAUNE:
                return Color.YELLOW;
            case ROUGE:
                return Color.RED;
            case BLEU:
                return Color.BLUE;
            case VERT:
                return Color.GREEN;
            case ROSE:
                return Color.PINK;
            default:
                return Color.WHITE;
        }
    }

    private String convertirEnHexadecimal(Color couleur) {
        int rouge = (int) (couleur.getRed() * 255);
        int vert = (int) (couleur.getGreen() * 255);
        int bleu = (int) (couleur.getBlue() * 255);
        return String.format("#%02X%02X%02X", rouge, vert, bleu);
    }




}

package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IJoueur.CouleurJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.data.CarteTransport;
import fr.umontpellier.iut.rails.mecanique.data.Couleur;
import fr.umontpellier.iut.rails.mecanique.data.TypeCarteTransport;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.SaisieNbPionsWagon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    private IJeu jeu;
    private Label nomJoueur;

    private HBox CarteJoueurCourant;


    private HBox carteJoueurCourantLigne1;

    private HBox carteJoueurCourantLigne2;

    private HBox nbPions;

    private HBox cartesDestination;

    private SimpleIntegerProperty nbPionsWagon;

    private SimpleIntegerProperty nbPionsBateau;

    private SimpleIntegerProperty nbPort;

    private SimpleIntegerProperty score;
    private VBox dest;


    public VueJoueurCourant(IJeu jeu) {

        this.jeu = jeu;

        this.setAlignment(Pos.TOP_CENTER);

        this.nomJoueur = new Label();
        this.nomJoueur.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        this.nomJoueur.setAlignment(Pos.TOP_CENTER);
        this.CarteJoueurCourant= new HBox();
        this.cartesDestination = new HBox();
        this.dest = new VBox();

        CarteJoueurCourant.setSpacing(5);

        carteJoueurCourantLigne1 = new HBox();
        carteJoueurCourantLigne2 = new HBox();

        VBox cartesContainer = new VBox(carteJoueurCourantLigne1, carteJoueurCourantLigne2);

        cartesContainer.setSpacing(5);
        cartesContainer.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        //this.setMinSize(500,500);




        this.setSpacing(5);
        this.setPrefWidth(550);
        this.setPrefHeight(420);

        //this.setTranslateY(50);
        this.setTranslateX(5);


        nbPions = new HBox();

        ImageView logoPionWagon = new ImageView("/images/bouton-pions-wagon.png");
        logoPionWagon.setPreserveRatio(true);
        logoPionWagon.setFitHeight(30);

        nbPionsBateau = new SimpleIntegerProperty();
        nbPionsWagon = new SimpleIntegerProperty();
        nbPort= new SimpleIntegerProperty();
        score = new SimpleIntegerProperty();

        Label nbPionsWagonLabel = new Label();
        nbPionsWagonLabel.textProperty().bind(nbPionsWagon.asString());
        nbPionsWagonLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nbPionsBateauLabel = new Label();
        nbPionsBateauLabel.textProperty().bind(nbPionsBateau.asString());
        nbPionsBateauLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label nbPortLabel= new Label();
        nbPortLabel.textProperty().bind(nbPort.asString());
        nbPortLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label scoreJoueur = new Label();
        scoreJoueur.textProperty().bind(score.asString());
        scoreJoueur.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ImageView logoPionBateau = new ImageView("/images/bouton-pions-bateau.png");
        logoPionBateau.setPreserveRatio(true);
        logoPionBateau.setFitHeight(30);

        ImageView logoPort= new ImageView("/images/port.png");
        logoPort.setPreserveRatio(true);
        logoPort.setFitHeight(30);



        HBox scoreHBox = new HBox();


        Label scoredeuxpoints = new Label("Score : ");

        scoreHBox.getChildren().addAll(scoredeuxpoints,scoreJoueur);
        scoreHBox.setSpacing(5);
        scoreHBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");







        nbPions.getChildren().addAll(logoPionWagon,nbPionsWagonLabel,logoPionBateau,nbPionsBateauLabel, logoPort, nbPortLabel,scoreHBox);



        nbPions.setSpacing(20);
        nbPions.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");

        this.getChildren().addAll(nomJoueur,cartesContainer,nbPions);





    }



    public void creerBindings(IJeu jeu) {
        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                this.supprimerCarte()
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nomJoueur.setText(nouveauJoueur.getNom())
        );



        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nbPionsWagon.bind(nouveauJoueur.nbPionsWagonsProperty())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nbPionsBateau.bind(nouveauJoueur.nbPionsBateauxProperty())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur)->
                score.bind(nouveauJoueur.scoreProperty())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur)->
                nbPort.bind(nouveauJoueur.nbPortsProperty())
        );



        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) -> {
            this.setStyle("-fx-background-color: " + RGB(couleurDuJoueur(nouveauJoueur)));
            afficheCarte();
            afficheDestination(nouveauJoueur);


        });
    }

    public void afficheCarte(){

        List<? extends ICarteTransport> cartes = this.jeu.joueurCourantProperty().get().getCartesTransport();


        carteJoueurCourantLigne1.getChildren().clear();
        carteJoueurCourantLigne2.getChildren().clear();

        for (int i = 0; i < cartes.size(); i++) {
            ICarteTransport icarte = cartes.get(i);
            VueCarteTransport carteTransport = new VueCarteTransport(icarte,1);

            if (i < 5) {
                carteJoueurCourantLigne1.getChildren().add(carteTransport);
            } else {
                carteJoueurCourantLigne2.getChildren().add(carteTransport);
            }
        }

    }

    public void afficheDestination(IJoueur joueur){
        List<? extends IDestination> cartes = joueur.getDestinations();

        dest = new VBox();

        for(IDestination i : cartes){
            VueDestination carteDestination = null;
            try {
                carteDestination = new VueDestination(i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dest.getChildren().add(carteDestination);
        }

    }

    public void supprimerCarte(){
        CarteJoueurCourant.getChildren().clear();
    }





    private Color couleurDuJoueur(IJoueur joueur) {
        switch (joueur.getCouleur()) {
            case JAUNE:
                return Color.BLUE;
            case ROUGE:
                return Color.RED;
            case BLEU:
                return Color.GREEN;
            case VERT:
                return Color.YELLOW;
            case ROSE:
                return Color.PINK;
            default:
                return Color.WHITE;
        }
    }

    private static String RGB(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }


}

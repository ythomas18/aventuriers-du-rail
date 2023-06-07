package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IJoueur.CouleurJoueur;
import fr.umontpellier.iut.rails.mecanique.Joueur;
import fr.umontpellier.iut.rails.mecanique.etatsJoueur.demandepions.SaisieNbPionsWagon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.text.NumberFormat;
import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;

    private HBox CarteJoueurCourant;


    private HBox carteJoueurCourantLigne1;

    private HBox carteJoueurCourantLigne2;

    private HBox nbPions;

    private SimpleIntegerProperty nbPionsWagon;

    private SimpleIntegerProperty nbPionsBateau;

    private SimpleIntegerProperty nbPort;

    private SimpleIntegerProperty score;



    public VueJoueurCourant(String nom_du_joueur) {

        this.setAlignment(Pos.TOP_CENTER);

        this.nomJoueur = new Label();
        this.nomJoueur.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        this.nomJoueur.setAlignment(Pos.TOP_CENTER);
        this.CarteJoueurCourant= new HBox();

        CarteJoueurCourant.setSpacing(5);

        carteJoueurCourantLigne1 = new HBox();
        carteJoueurCourantLigne2 = new HBox();

        VBox cartesContainer = new VBox(carteJoueurCourantLigne1, carteJoueurCourantLigne2);

        cartesContainer.setSpacing(5);
        //this.setMinSize(500,500);




        this.setSpacing(5);
        this.setPrefWidth(500);

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

        ImageView logoPionBateau = new ImageView("/images/bouton-pions-bateau.png");
        logoPionBateau.setPreserveRatio(true);
        logoPionBateau.setFitHeight(30);

        ImageView logoPort= new ImageView("/images/port.png");
        logoPort.setPreserveRatio(true);
        logoPort.setFitHeight(30);

        Label scoreJoueur = new Label();

        HBox scoreHBox = new HBox();

        scoreJoueur.textProperty().bind(score.asString());
        Label scoredeuxpoints = new Label("Score : ");

        scoreHBox.getChildren().addAll(scoredeuxpoints,scoreJoueur);
        scoreHBox.setSpacing(5);
        scoreHBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");




        nbPions.getChildren().addAll(logoPionWagon,nbPionsWagonLabel,logoPionBateau,nbPionsBateauLabel, logoPort, nbPortLabel,scoreHBox);

        nbPions.setSpacing(20);
        nbPions.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");



        this.getChildren().addAll(nomJoueur,cartesContainer,nbPions);
        this.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 2px;");










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

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nbPionsWagon.bind(nouveauJoueur.nbPionsWagonsProperty())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) ->
                nbPionsBateau.bind(nouveauJoueur.nbPionsBateauxProperty())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur)->
                score.set(nouveauJoueur.getScore())
        );

        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur)->
                nbPort.set(nouveauJoueur.getNbPorts())
        );



        jeu.joueurCourantProperty().addListener((observableValue, ancienJoueur, nouveauJoueur) -> {
            this.setStyle(CouleurJoueur(nouveauJoueur));

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

    public String CouleurJoueur(IJoueur joueur){
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


}

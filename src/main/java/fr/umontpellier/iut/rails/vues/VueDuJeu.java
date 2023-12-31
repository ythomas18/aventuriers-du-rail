package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.ICarteTransport;
import fr.umontpellier.iut.rails.IDestination;
import fr.umontpellier.iut.rails.IJeu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.awt.Desktop;

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

    private HBox listeCarteTransport;

    private HBox piocheOption;

    TextField saisieNombreDePions;


    private final ListChangeListener<IDestination> listenerCarteDestination = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (IDestination iDestination : change.getAddedSubList()) {
                    try {
                        VueDestination vd = new VueDestination(iDestination);
                        listeDestination.getChildren().add(vd);
                        vd.getChildren().get(0).setOnMouseClicked(event -> {
                            this.getJeu().uneDestinationAEteChoisie(vd.getDestination());
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (change.wasRemoved()) {
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

    private final ListChangeListener<ICarteTransport> listenerCarteTransport = change -> {
        Platform.runLater(() -> {
            List<? extends ICarteTransport> cardWagonVisibles = change.getList();

            this.listeCarteTransport.getChildren().clear();
            for(int i=0; i<cardWagonVisibles.size(); i++){
                VueCarteTransport vcw = new VueCarteTransport(cardWagonVisibles.get(i),1);
                this.listeCarteTransport.getChildren().addAll(vcw);

                vcw.setOnMouseClicked(event -> {
                    this.getJeu().uneCarteTransportAEteChoisie(vcw.getCarteTransport());
                });
            }
        });
    };


    private final ChangeListener<String> listenerTextField = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (newValue.contains("Saisissez un nombre de pions")) {
                saisieNombreDePions.visibleProperty().setValue(true);
            }
            else {
                saisieNombreDePions.visibleProperty().setValue(false);
            }
        }
    };






    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau(jeu);
        HBox plat = new HBox();
        this.setStyle("-fx-background-color: #C8AD7F");
        double borderWidth = 2.0;
        this.piocheOption = new HBox();
        VBox pioche = new VBox();
        this.listeDestination = new HBox();
        this.listeCarteTransport = new HBox();
        HBox bas = new HBox();
        VBox vBox = new VBox();
        HBox deuxPartie = new HBox();
        HBox logo = new HBox();
        HBox ajoutBouton= new HBox();




        ImageView logopiochebateau = new ImageView("images/cartesWagons/dos-BATEAU.png");
        logopiochebateau.setPreserveRatio(true);
        logopiochebateau.setFitHeight(200);

        logopiochebateau.setOnMouseClicked(event -> {
            this.getJeu().uneCarteBateauAEtePiochee();
        });


        ImageView logopiochewagon = new ImageView("images/cartesWagons/dos-WAGON.png");
        logopiochewagon.setPreserveRatio(true);
        logopiochewagon.setFitHeight(200);

        logopiochewagon.setOnMouseClicked(event -> {
            this.getJeu().uneCarteWagonAEtePiochee();
        });

        ImageView logopiochedestination = new ImageView("images/cartesWagons/destinations.png");
        logopiochedestination.setPreserveRatio(true);
        logopiochedestination.setFitHeight(130);

        logopiochedestination.setOnMouseClicked(mouseEvent -> {
            this.getJeu().nouvelleDestinationDemandee();
        });


        ImageView logoPionBateau = new ImageView("images/bouton-pions-bateau.png");
        logoPionBateau.setPreserveRatio(true);
        logoPionBateau.setFitHeight(70);

        logoPionBateau.setOnMouseClicked(event -> {
            this.getJeu().nouveauxPionsBateauxDemandes();
        });

        ImageView logoPionWagon = new ImageView("images/bouton-pions-wagon.png");
        logoPionWagon.setPreserveRatio(true);
        logoPionWagon.setFitHeight(70);

        logoPionWagon.setOnMouseClicked(event -> {
            this.getJeu().nouveauxPionsWagonsDemandes();
        });

        logo.getChildren().addAll(logoPionWagon, logoPionBateau);
        pioche.getChildren().addAll(logo, logopiochedestination);
        piocheOption.getChildren().addAll(logopiochebateau, logopiochewagon, pioche);
        deuxPartie.getChildren().addAll(vBox, piocheOption);
        deuxPartie.setSpacing(450);


        plateau.setMaxSize(1000, 1000);
        plat.setMaxSize(3000, 3000);
        VueJoueurCourant vueJoueurCourant = new VueJoueurCourant(this.jeu);
        vueJoueurCourant.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(borderWidth))));
        vueJoueurCourant.creerBindings(jeu);
        VueAutresJoueurs vueAutresJoueurs = new VueAutresJoueurs(jeu);


        VBox joueurCourantXAutresJoueurs = new VBox();
        joueurCourantXAutresJoueurs.getChildren().addAll(vueJoueurCourant, vueAutresJoueurs);
        joueurCourantXAutresJoueurs.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));


        DropShadow ds = new DropShadow();
        ds.setRadius(10);
        ds.setColor(Color.BLACK);
        ds.setOffsetX(3);
        ds.setOffsetY(3);

        this.plateau.setEffect(ds);


        plat.getChildren().addAll(plateau, joueurCourantXAutresJoueurs);
        this.getChildren().add(plat);

        Label labelDestinationInitiale = new Label();
        Button btnPasser = new Button("Passer");
        Button btnRegle= new Button("Règle du jeux");
        btnRegle.setPrefSize(500,20);
        btnPasser.setPrefSize(500,20);
        DropShadow effet= new DropShadow();
        effet.setColor(Color.BLACK);
        effet.setOffsetX(2);
        effet.setOffsetY(2);
        btnPasser.setEffect(effet);
        btnPasser.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 5px;");
        btnPasser.setOnMouseEntered(e -> {
            btnPasser.setStyle("-fx-background-color: #FFE6C7; -fx-background-radius: 25px");
        });
        btnPasser.setOnMouseExited(e -> {
            btnPasser.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        });

        btnRegle.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 5px;");
        btnRegle.setOnMouseEntered(e -> {
            btnRegle.setStyle("-fx-background-color: #FFE6C7; -fx-background-radius: 25px");
        });
        btnRegle.setOnMouseExited(e -> {
            btnRegle.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        });
        btnRegle.setEffect(effet);


        btnRegle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Path path = Paths.get("C:\\Users\\yannt\\IdeaProjects\\railsihm\\documents\\Aventuriers du rail autour du monde - Règles.pdf");
                    File fichier= new File(path.toUri());
                    Desktop.getDesktop().open(fichier);
                }
                catch (IOException ioException){
                    ioException.printStackTrace();
                }
            }
        });

        Label lblInstructions = new Label();

        lblInstructions.textProperty().bind(jeu.instructionProperty());
        lblInstructions.setStyle("-fx-font-weight: Bold; -fx-font-size: 20px;");
        lblInstructions.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));



        listeDestination.setSpacing(40);

        //this.getChildren().addAll(lblInstructions, btnPasser,listeDestination);

        EventHandler<MouseEvent> btnPasserHandlerClick = MouseEvent -> {
            jeu.passerAEteChoisi();
        };



        btnPasser.setOnMouseClicked(btnPasserHandlerClick);



        jeu.destinationsInitialesProperty().addListener(listenerCarteDestination);

        jeu.cartesTransportVisiblesProperty().addListener(listenerCarteTransport);



        saisieNombreDePions = new TextField();




        ajoutBouton.getChildren().add(btnRegle);
        //ajoutBouton.setSpacing(10);
        ajoutBouton.setPadding(new Insets(5));
        bas.setPadding(new Insets(5));
        bas.getChildren().add(btnPasser);
        vBox.getChildren().addAll(ajoutBouton,bas, lblInstructions, saisieNombreDePions, listeDestination, listeCarteTransport);
        vBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        vBox.setPrefSize(800, 500);

        this.getChildren().add(deuxPartie);


        EventHandler<KeyEvent> action = KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                jeu.leNombreDePionsSouhaiteAEteRenseigne(saisieNombreDePions.getText());

            }
        };









        jeu.instructionProperty().addListener(listenerTextField);







        saisieNombreDePions.setOnKeyPressed(action);




        //jeu.cartesTransportVisiblesProperty().addListener(listenerCarteTransport);



    //this.setBackground(new Background(new BackgroundImage(new Image("images/fonds/papier_froise.png"), null, null , null, null)));
    // exemple coordonnées dans le dossier resources : "images/cartesWagons/avatar-BLEU.png"
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

    public VueCarteTransport removeCarteTransport(ICarteTransport carteTransport) {
        for(Node n : listeCarteTransport.getChildren()){
            VueCarteTransport carteTransport1 = (VueCarteTransport) n;
            if(carteTransport1.equals(new VueCarteTransport(carteTransport,1))){
                return (VueCarteTransport) n;
            }
        }
        return null;

    }

    public int choixNombreDePions() {
        return Integer.parseInt(saisieNombreDePions.getText());
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

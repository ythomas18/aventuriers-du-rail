package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.RailsIHM;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends GridPane {

    private final ObservableList<String> nomsJoueurs;
    private RailsIHM railsIHM;

    HBox titleBox;
    ImageView imageTitle;
    Label selectJoueurTitle;

    ImageView imgPlus;
    ImageView imgMoins;
    SimpleIntegerProperty nbJoueur;
    Label nbLabelJoueur;
    HBox nbJoueurBox;
    Button btnMoins;
    Button btnPlus;

    HBox joueurBox;

    ImageView j1img;
    TextField j1tf;

    ImageView j2img;
    TextField j2tf;
    VBox j3Box;
    ImageView j3img;
    TextField j3tf;

    ImageView j4img;
    TextField j4tf;
    ImageView j5img;
    TextField j5tf;
    Button jouer;
    HBox playButtonBox;
    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public VueChoixJoueurs(RailsIHM railsIHM) {
        this.railsIHM = railsIHM;
        nomsJoueurs = FXCollections.observableArrayList();

        this.setPrefWidth(1600);
        this.setPrefHeight(900);
        this.setStyle("-fx-background-color: #C8AD7F");

        titleBox = new HBox();
        titleBox.setPrefWidth(this.getPrefWidth());
        imageTitle = new ImageView(new Image("images/logo.png"));
        imageTitle.setFitHeight(130);
        imageTitle.setFitWidth(800);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(imageTitle);
        titleBox.setTranslateY(35);

        selectJoueurTitle = new Label("Choisissez le nombre de joueur voulu :");
        selectJoueurTitle.setFont(Font.font("Cabin", FontWeight.BLACK, 22));
        selectJoueurTitle.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));
        selectJoueurTitle.setTranslateY(75);
        selectJoueurTitle.setTranslateX(45);

        imgPlus = new ImageView(new Image("images/plus.png"));
        imgPlus.setFitWidth(45);
        imgPlus.setFitHeight(45);
        btnPlus = new Button();
        btnPlus.setGraphic(imgPlus);
        btnPlus.setPrefWidth(40);
        btnPlus.setPrefHeight(40);
        btnPlus.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        btnPlus.setOnMouseClicked(e -> {
            if(this.nbJoueur.getValue() < 5){
                this.nbJoueur.set(this.nbJoueur.getValue()+1);
            }
        });

        imgMoins = new ImageView(new Image("images/moins.png"));
        imgMoins.setFitHeight(50);
        imgMoins.setFitWidth(40);
        btnMoins = new Button();
        btnMoins.setGraphic(imgMoins);
        btnMoins.setPrefWidth(40);
        btnMoins.setPrefHeight(50);
        btnMoins.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        btnMoins.setOnMouseClicked(e -> {
            if(this.nbJoueur.getValue() > 2){
                this.nbJoueur.set(this.nbJoueur.getValue()-1);
            }
        });

        nbJoueur = new SimpleIntegerProperty(2);
        nbLabelJoueur = new Label(nbJoueur.getValue() + " / 5");
        nbLabelJoueur.setTranslateX(5);
        nbLabelJoueur.setFont(Font.font("Cabin", FontWeight.MEDIUM, 22));
        nbJoueurBox = new HBox();
        nbJoueurBox.getChildren().addAll(btnMoins, nbLabelJoueur, btnPlus);
        nbJoueurBox.setAlignment(Pos.CENTER);
        nbJoueurBox.setPrefWidth(this.getPrefWidth());
        nbJoueurBox.setSpacing(25);
        nbJoueurBox.setTranslateY(110);


        joueurBox = new HBox();

        VBox j1Box = new VBox();
        j1img = new ImageView(new Image("images/cartesWagons/avatar-BLEU.png"));
        j1img.setFitWidth(150);
        j1img.setFitHeight(175);
        j1img.setTranslateX(22.5);
        j1tf = new TextField();
        j1tf.setTranslateY(10);
        j1tf.setPromptText("Insérez un nom pour le Joueur 1");
        j1tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j1tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j1Box.getChildren().addAll(j1img, j1tf);
        j1Box.setTranslateX(5);

        VBox j2Box = new VBox();
        j2img = new ImageView(new Image("images/cartesWagons/avatar-JAUNE.png"));
        j2img.setFitWidth(150);
        j2img.setFitHeight(175);
        j2img.setTranslateX(22.5);
        j2tf = new TextField();
        j2tf.setTranslateY(10);
        j2tf.setPromptText("Insérez un nom pour le Joueur 2");
        j2tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j2tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j2Box.getChildren().addAll(j2img, j2tf);
        j2Box.setTranslateX(5);

        VBox j3Box = new VBox();
        j3img = new ImageView(new Image("images/cartesWagons/avatar-ROSE.png"));
        j3img.setFitWidth(150);
        j3img.setFitHeight(175);
        j3img.setTranslateX(22.5);
        j3tf = new TextField();
        j3tf.setTranslateY(10);
        j3tf.setPromptText("Insérez un nom pour le Joueur 3");
        j3tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j3tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j3Box.getChildren().addAll(j3img, j3tf);
        j3Box.setTranslateX(5);

        VBox j4Box = new VBox();
        j4img = new ImageView(new Image("images/cartesWagons/avatar-ROUGE.png"));
        j4img.setFitWidth(150);
        j4img.setFitHeight(175);
        j4img.setTranslateX(22.5);
        j4tf = new TextField();
        j4tf.setTranslateY(10);
        j4tf.setPromptText("Insérez un nom pour le Joueur 4");
        j4tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j4tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j4Box.getChildren().addAll(j4img, j4tf);
        j4Box.setTranslateX(5);

        VBox j5Box = new VBox();
        j5img = new ImageView(new Image("images/cartesWagons/avatar-VERT.png"));
        j5img.setFitWidth(150);
        j5img.setFitHeight(175);
        j1img.setTranslateX(22.5);
        j5tf = new TextField();
        j5tf.setTranslateY(10);
        j5tf.setPromptText("Insérez un nom pour le Joueur 5");
        j5tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
        j5tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
        j5Box.getChildren().addAll(j5img, j5tf);
        j5Box.setTranslateX(5);

        joueurBox.getChildren().addAll(j1Box,j2Box);
        joueurBox.setSpacing(10.0);
        joueurBox.setAlignment(Pos.CENTER);
        joueurBox.setTranslateY(150);

        playButtonBox = new HBox();
        jouer = new Button("Jouer");
        jouer.setPrefHeight(50);
        jouer.setPrefWidth(350);
        jouer.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        jouer.setFont(Font.font("Cabin", FontWeight.MEDIUM, 22));
        jouer.setOnMouseEntered(e -> {
            jouer.setStyle("-fx-background-color: #FFE6C7; -fx-background-radius: 25px");
        });
        jouer.setOnMouseExited(e -> {
            jouer.setStyle("-fx-background-color: #F6E7D4; -fx-background-radius: 25px");
        });
        jouer.setAlignment(Pos.CENTER);

        DropShadow ds = new DropShadow();
        ds.setRadius(10);
        ds.setColor(Color.BLACK);
        ds.setOffsetX(3);
        ds.setOffsetY(3);
        jouer.setEffect(ds);
        jouer.setOnAction(e -> {
            this.enterNamePlayer();
            this.railsIHM.demarrerPartie();
        });
        playButtonBox.getChildren().addAll(jouer);
        playButtonBox.setMaxWidth(this.getMaxWidth());
        playButtonBox.setTranslateY(400);
        playButtonBox.setAlignment(Pos.BOTTOM_CENTER);

        j5Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j4Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j3Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j2Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        j1Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        this.addRow(0, titleBox);
        this.addRow(1, selectJoueurTitle);
        this.addRow(2, nbJoueurBox);
        this.addRow(3, joueurBox);
        this.addRow(4, playButtonBox);

        this.createBindings();
    }

    public void createBindings(){
        this.nbJoueur.addListener(e -> {
            Platform.runLater(() -> {
                this.nbLabelJoueur.setText(this.nbJoueur.getValue() + " / 5");

                this.joueurBox.getChildren().clear();

                VBox j1Box = new VBox();
                j1img = new ImageView(new Image("images/cartesWagons/avatar-BLEU.png"));
                j1img.setFitWidth(this.getWidth()/10);
                j1img.setFitHeight(this.getWidth()/8);
                j1img.setTranslateX(22.5);
                j1tf = new TextField();
                j1tf.setTranslateY(10);
                j1tf.setPrefWidth(this.getWidth()/8);
                j1tf.setPromptText("Insérez un nom pour le Joueur 1");
                j1tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
                j1tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
                j1Box.getChildren().addAll(j1img, j1tf);
                j1Box.setTranslateX(5);

                VBox j2Box = new VBox();
                j2img = new ImageView(new Image("images/cartesWagons/avatar-JAUNE.png"));
                j2img.setFitWidth(this.getWidth()/10);
                j2img.setFitHeight(this.getWidth()/8);
                j2img.setTranslateX(22.5);
                j2tf = new TextField();
                j2tf.setTranslateY(10);
                j2tf.setPrefWidth(this.getWidth()/8);
                j2tf.setPromptText("Insérez un nom pour le Joueur 2");
                j2tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
                j2tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
                j2Box.getChildren().addAll(j2img, j2tf);
                j2Box.setTranslateX(5);

                VBox j3Box = new VBox();
                j3img = new ImageView(new Image("images/cartesWagons/avatar-ROSE.png"));
                j3img.setFitWidth(this.getWidth()/10);
                j3img.setFitHeight(this.getWidth()/8);
                j3img.setTranslateX(22.5);
                j3tf = new TextField();
                j3tf.setTranslateY(10);
                j3tf.setPrefWidth(this.getWidth()/8);
                j3tf.setPromptText("Insérez un nom pour le Joueur 3");
                j3tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
                j3tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
                j3Box.getChildren().addAll(j3img, j3tf);
                j3Box.setTranslateX(5);

                VBox j4Box = new VBox();
                j4img = new ImageView(new Image("images/cartesWagons/avatar-ROUGE.png"));
                j4img.setFitWidth(this.getWidth()/10);
                j4img.setFitHeight(this.getWidth()/8);
                j4img.setTranslateX(22.5);
                j4tf = new TextField();
                j4tf.setTranslateY(10);
                j4tf.setPrefWidth(this.getWidth()/8);
                j4tf.setPromptText("Insérez un nom pour le Joueur 4");
                j4tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
                j4tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
                j4Box.getChildren().addAll(j4img, j4tf);
                j4Box.setTranslateX(5);

                VBox j5Box = new VBox();
                j5img = new ImageView(new Image("images/cartesWagons/avatar-VERT.png"));
                j5img.setFitWidth(this.getWidth()/10);
                j5img.setFitHeight(this.getWidth()/8);
                j5img.setTranslateX(22.5);
                j5tf = new TextField();
                j5tf.setTranslateY(10);
                j5tf.setPrefWidth(this.getWidth()/8);
                j5tf.setPromptText("Insérez un nom pour le Joueur 5");
                j5tf.setFont(Font.font("Cabin", FontPosture.REGULAR, 15));
                j5tf.setStyle("-fx-background-color: #E5E5E5; -fx-background-radius: 5px; -fx-border-color: #B9B9B9; -fx-border-radius: 5px");
                j5Box.getChildren().addAll(j5img, j5tf);
                j5Box.setTranslateX(5);

                j1img.setPreserveRatio(true);
                j2img.setPreserveRatio(true);
                j3img.setPreserveRatio(true);
                j4img.setPreserveRatio(true);
                j5img.setPreserveRatio(true);

                j5Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                j4Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                j3Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                j2Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                j1Box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));



                if(this.nbJoueur.getValue() == 2){
                    joueurBox.getChildren().addAll(j1Box,j2Box);
                }else if(this.nbJoueur.getValue() == 3){
                    joueurBox.getChildren().addAll(j1Box,j2Box,j3Box);
                }else if(this.nbJoueur.getValue() == 4){
                    joueurBox.getChildren().addAll(j1Box,j2Box,j3Box,j4Box);
                }else if(this.nbJoueur.getValue() == 5){
                    joueurBox.getChildren().addAll(j1Box,j2Box,j3Box,j4Box,j5Box);
                }

            });
        });
        this.createRedimensionnement();
    }
    public void enterNamePlayer(){
        if(this.nbJoueur.getValue() == 2){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
        }else if(this.nbJoueur.getValue() == 3){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
        }else if(this.nbJoueur.getValue() == 4){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
            this.nomsJoueurs.add(3, this.j4tf.getText());
        }else if(this.nbJoueur.getValue() == 5){
            this.nomsJoueurs.add(0, this.j1tf.getText());
            this.nomsJoueurs.add(1, this.j2tf.getText());
            this.nomsJoueurs.add(2, this.j3tf.getText());
            this.nomsJoueurs.add(3, this.j4tf.getText());
            this.nomsJoueurs.add(4, this.j5tf.getText());
        }
    }


    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {
        nomsJoueurs.addListener((ListChangeListener<String>) change -> quandLeNombreDeJoueursChange.changed(null, null, getNombreDeJoueurs()));
    }

    public void createRedimensionnement(){
        this.heightProperty().addListener(e -> {
            this.imageTitle.setFitHeight(this.getHeight()/8);
            this.playButtonBox.setTranslateY(this.getHeight()/3);
            this.imgPlus.setFitHeight(this.getHeight()/30);
            this.imgPlus.setFitWidth(this.getHeight()/30);
            this.jouer.setPrefHeight(this.getHeight()/50);

            this.j1img.setFitHeight(this.getHeight()/5);
            this.j2img.setFitHeight(this.getHeight()/5);
            this.j3img.setFitHeight(this.getHeight()/5);
            this.j4img.setFitHeight(this.getHeight()/5);
            this.j5img.setFitHeight(this.getHeight()/5);
            this.j1img.setFitWidth(this.getHeight()/6);
            this.j2img.setFitWidth(this.getHeight()/6);
            this.j3img.setFitWidth(this.getHeight()/6);
            this.j4img.setFitWidth(this.getHeight()/6);
            this.j5img.setFitWidth(this.getHeight()/6);
        });
        this.widthProperty().addListener(e -> {
            this.imageTitle.setFitWidth(this.getWidth()/2);
            this.titleBox.setPrefWidth(this.getWidth());
            this.selectJoueurTitle.setFont(Font.font("Cabin", FontPosture.REGULAR, this.getWidth()/70));
            this.nbLabelJoueur.setFont(Font.font("Cabin", FontPosture.REGULAR, this.getWidth()/70));
            this.imgMoins.setFitWidth(this.getPrefWidth()/70);
            this.imgPlus.setFitWidth(this.getPrefWidth()/70);
            this.imgPlus.setFitHeight(this.getPrefHeight()/70);
            this.jouer.setFont(Font.font("Cabin", FontPosture.REGULAR, this.getWidth()/70));
            this.jouer.setPrefWidth(this.getWidth()/5);

            this.j1tf.setPrefWidth(this.getWidth()/8);
            this.j1img.setFitWidth(this.getWidth()/10);
            this.j1img.setFitHeight(this.getWidth()/8);
            this.j2tf.setPrefWidth(this.getWidth()/8);
            this.j2img.setFitWidth(this.getWidth()/10);
            this.j2img.setFitHeight(this.getWidth()/8);
            this.j3tf.setPrefWidth(this.getWidth()/8);
            this.j3img.setFitWidth(this.getWidth()/10);
            this.j3img.setFitHeight(this.getWidth()/8);
            this.j4tf.setPrefWidth(this.getWidth()/8);
            this.j4img.setFitWidth(this.getWidth()/10);
            this.j4img.setFitHeight(this.getWidth()/8);
            this.j5tf.setPrefWidth(this.getWidth()/8);
            this.j5img.setFitWidth(this.getWidth()/10);
            this.j5img.setFitHeight(this.getWidth()/8);
        });
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            //hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return this.nomsJoueurs.size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        switch ((playerNumber)) {
            case 1:
                return "Guybrush";
            case 2:
                return "Largo";
            case 3:
                return "LeChuck";
            case 4:
                return  "Elaine";
            default:
                return null;
        }
    }

}

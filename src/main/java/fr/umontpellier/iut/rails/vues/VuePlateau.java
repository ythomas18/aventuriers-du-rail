package fr.umontpellier.iut.rails.vues;

import fr.umontpellier.iut.rails.IJeu;
import fr.umontpellier.iut.rails.IJoueur;
import fr.umontpellier.iut.rails.IRoute;
import fr.umontpellier.iut.rails.IVille;
import fr.umontpellier.iut.rails.mecanique.data.Ville;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 * <p>
 * On y définit les handlers à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront à jour le plateau après la prise d'une route ou d'un port par un joueur
 */
public class VuePlateau extends Pane {

    @FXML
    private ImageView mapMonde;

    private IJeu jeu;

    private Group routes;

    private Group ports;





    public VuePlateau(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMinSize(Screen.getPrimary().getBounds().getWidth() / 3, Screen.getPrimary().getBounds().getHeight() / 3);
        this.jeu = jeu;


    }


    EventHandler<MouseEvent> choixRoute = event -> {
        Rectangle rectRoute = (Rectangle) event.getSource();
        String StrRoute = rectRoute.getId();
        System.out.println(StrRoute);
        jeu.uneRouteAEteChoisie(StrRoute);


    };

    EventHandler<MouseEvent> choixPort = event -> {
        Circle cerclePort = (Circle) event.getSource();
        System.out.println(cerclePort);
        String StrPort = cerclePort.getId();
        System.out.println(StrPort);
        jeu.unPortAEteChoisi(StrPort);
    };

    public void creerBindings() {
        ajouterVilles();
        ajouterPorts();
        ajouterRoutes();
        bindRedimensionEtCentragePlateau();
    }

    private void ajouterPorts() {
        List<? extends IVille> listePorts = ((VueDuJeu) getScene().getRoot()).getJeu().getPorts();
        for (String nomPort : DonneesGraphiques.ports.keySet()) {
            IVille port = listePorts.stream().filter(r -> r.getNom().equals(nomPort)).findAny().orElse(null);
            DonneesGraphiques.DonneesCerclesPorts positionPortSurPlateau = DonneesGraphiques.ports.get(nomPort);
            Circle cerclePort = new Circle(positionPortSurPlateau.centreX(), positionPortSurPlateau.centreY(), DonneesGraphiques.rayonInitial);
            cerclePort.setId(nomPort);
            getChildren().add(cerclePort);
            bindCerclePortAuPlateau(positionPortSurPlateau, cerclePort);
            cerclePort.setOnMouseClicked(choixPort);
            port.proprietaireProperty().addListener(e -> {
                if (port.proprietaireProperty() != null) {
                    cerclePortPris(port.getNom(), port.getProprietaire());
                }
            });
        }
    }

    private void ajouterRoutes() {
        List<? extends IRoute> listeRoutes = ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes();
        for (String nomRoute : DonneesGraphiques.routes.keySet()) {
            ArrayList<DonneesGraphiques.DonneesSegments> segmentsRoute = DonneesGraphiques.routes.get(nomRoute);
            IRoute route = listeRoutes.stream().filter(r -> r.getNom().equals(nomRoute)).findAny().orElse(null);
            for (DonneesGraphiques.DonneesSegments unSegment : segmentsRoute) {
                Rectangle rectangleSegment = new Rectangle(unSegment.getXHautGauche(), unSegment.getYHautGauche(), DonneesGraphiques.largeurRectangle, DonneesGraphiques.hauteurRectangle);
                rectangleSegment.setId(nomRoute);
                rectangleSegment.setRotate(unSegment.getAngle());
                rectangleSegment.setFill(Color.TRANSPARENT);
                getChildren().add(rectangleSegment);
                rectangleSegment.setOnMouseClicked(choixRoute);
                route.proprietaireProperty().addListener(e -> {
                    if (route.proprietaireProperty() != null) {
                        rectangleRoutePrise(route.getNom(), route.getProprietaire());
                    }
                });
                bindRectangle(rectangleSegment, unSegment.getXHautGauche(), unSegment.getYHautGauche());
            }
        }
    }

    public void rectangleRoutePrise(String routePrise, IJoueur joueur) {
        List<? extends IRoute> listeRoutes = ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes();
        for (String nomRoute : DonneesGraphiques.routes.keySet()) {
            if (nomRoute.equals(routePrise)) {
                ArrayList<DonneesGraphiques.DonneesSegments> segmentsRoute = DonneesGraphiques.routes.get(nomRoute);
                for (DonneesGraphiques.DonneesSegments unSegment : segmentsRoute) {
                    Rectangle rectangleSegment = new Rectangle(unSegment.getXHautGauche(), unSegment.getYHautGauche(), DonneesGraphiques.largeurRectangle, DonneesGraphiques.hauteurRectangle);
                    rectangleSegment.setFill(couleurDuJoueur(joueur));
                    rectangleSegment.setStroke(Color.BLACK);
                    rectangleSegment.setStrokeWidth(2);
                    rectangleSegment.setId(nomRoute);
                    rectangleSegment.setRotate(unSegment.getAngle());
                    getChildren().add(rectangleSegment);
                    bindRectangle(rectangleSegment, unSegment.getXHautGauche(), unSegment.getYHautGauche());
                }
            }
        }
    }

    public void cerclePortPris(String portPris, IJoueur joueur){
        for (String nomPort : DonneesGraphiques.ports.keySet()) {
            if (nomPort.equals(portPris)) {
                DonneesGraphiques.DonneesCerclesPorts positionPortSurPlateau = DonneesGraphiques.ports.get(nomPort);
                Circle cerclePort = new Circle(positionPortSurPlateau.centreX(), positionPortSurPlateau.centreY(), DonneesGraphiques.rayonInitial);
                cerclePort.setFill(couleurDuJoueur(joueur));
                cerclePort.setStroke(Color.BLACK);
                cerclePort.setStrokeWidth(2);
                getChildren().add(cerclePort);
                bindCerclePortAuPlateau(positionPortSurPlateau, cerclePort);
            }
        }

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

    private void bindRedimensionEtCentragePlateau() {
        mapMonde.fitWidthProperty().bind(widthProperty());
        mapMonde.fitHeightProperty().bind(heightProperty());
        mapMonde.layoutXProperty().bind(new DoubleBinding() { // Pour maintenir le plateau au centre
            {
                super.bind(widthProperty(), heightProperty());
            }

            @Override
            protected double computeValue() {
                double imageViewWidth = mapMonde.getLayoutBounds().getWidth();
                return (getWidth() - imageViewWidth) / 2;
            }
        });
    }

    private void bindCerclePortAuPlateau(DonneesGraphiques.DonneesCerclesPorts port, Circle cerclePort) {
        cerclePort.centerXProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return mapMonde.getLayoutX() + port.centreX() * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        cerclePort.centerYProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return mapMonde.getLayoutY() + port.centreY() * mapMonde.getLayoutBounds().getHeight() / DonneesGraphiques.hauteurInitialePlateau;
            }
        });
        cerclePort.radiusProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesGraphiques.rayonInitial * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {
        rect.widthProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesGraphiques.largeurRectangle * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.heightProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return DonneesGraphiques.hauteurRectangle * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty(), mapMonde.xProperty());
            }

            @Override
            protected double computeValue() {
                return mapMonde.getLayoutX() + layoutX * mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.xProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty(), mapMonde.xProperty());
            }

            @Override
            protected double computeValue() {
                return mapMonde.getLayoutBounds().getWidth() / DonneesGraphiques.largeurInitialePlateau;
            }
        });
        rect.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return layoutY * mapMonde.getLayoutBounds().getHeight() / DonneesGraphiques.hauteurInitialePlateau;
            }
        });
        rect.yProperty().bind(new DoubleBinding() {
            {
                super.bind(mapMonde.fitWidthProperty(), mapMonde.fitHeightProperty());
            }

            @Override
            protected double computeValue() {
                return mapMonde.getLayoutBounds().getHeight() / DonneesGraphiques.hauteurInitialePlateau;
            }
        });
    }


    private void ajouterVilles() {
        for (String nomVille : DonneesGraphiques.villes.keySet()) {
            DonneesGraphiques.DonneesCerclesPorts positionVilleSurPlateau = DonneesGraphiques.villes.get(nomVille);
            Circle cercleVille = new Circle(positionVilleSurPlateau.centreX(), positionVilleSurPlateau.centreY(), DonneesGraphiques.rayonInitial);
            cercleVille.setId(nomVille);
            getChildren().add(cercleVille);
            bindCerclePortAuPlateau(positionVilleSurPlateau, cercleVille);
            cercleVille.setOnMouseClicked(choixPort);
        }
    }






}

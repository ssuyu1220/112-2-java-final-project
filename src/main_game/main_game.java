package main_game;
import javafx.fxml.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

class PausePage extends Pane {

    private Group backgroundGroup = new Group();
    private Stage stage = new Stage();
    private BorderPane startpane = new BorderPane();
    private BorderPane contigamepane = new BorderPane();
    private BorderPane newgamPane = new BorderPane();
    private eelsAndEscalator origin;
    private Scene origionscene;

    File restartfile = new File("resources/restart.jpg");
    Image restartImage = new Image(restartfile.toURI().toString());
    ImageView restartImageView = new ImageView(restartImage);

    File homefile = new File("resources/home.jpg");
    Image homeImage = new Image(homefile.toURI().toString());
    ImageView homeImageView = new ImageView(homeImage);

    File continuefile = new File("resources/continue.jpg");
    Image continueImage = new Image(continuefile.toURI().toString());
    ImageView continueImageView = new ImageView(continueImage);

    public PausePage(Stage stage, BorderPane pane, eelsAndEscalator originEelsAndEscalator, Scene origionScene) {
        this.stage = stage;
        startpane = pane;
        // 原本正在進行的遊戲
        contigamepane.setCenter(originEelsAndEscalator);
        // 新的遊戲
        eelsAndEscalator newEelsAndEscalator = new eelsAndEscalator();
        newgamPane.setCenter(newEelsAndEscalator);
        // 指派本來在進行的遊戲給origin
        origin = originEelsAndEscalator;
        origionscene = origionScene;
        getChildren().addAll(backgroundGroup, restartImageView, homeImageView, continueImageView);
        Pause();
    }

    public void Pause() {

        double hie = getHeight();
        double wid = getWidth();

        restartImageView.setLayoutX(wid * 0.37);
        restartImageView.setLayoutY(hie * 0.45);
        restartImageView.setFitWidth(wid * 0.05);
        restartImageView.setFitHeight(hie * 0.05);

        homeImageView.setLayoutX(wid * 0.47);
        homeImageView.setLayoutY(hie * 0.45);
        homeImageView.setFitWidth(wid * 0.05);
        homeImageView.setFitHeight(hie * 0.05);

        continueImageView.setLayoutX(wid * 0.57);
        continueImageView.setLayoutY(hie * 0.45);
        continueImageView.setFitWidth(wid * 0.05);
        continueImageView.setFitHeight(hie * 0.05);

        Rectangle backgroundRec = new Rectangle();
        backgroundRec.setWidth(wid * 2);
        backgroundRec.setHeight(hie * 2.2);
        backgroundRec.setFill(Color.WHITE);
        backgroundRec.setStroke(Color.BLACK);
        backgroundRec.setLayoutX(0);
        backgroundRec.setLayoutY(0);
        backgroundGroup.getChildren().add(backgroundRec);

        restartImageView.setOnMouseClicked(re -> {
            Button restarButton = new Button("PAUSE");
            restarButton.styleProperty().bind(Bindings.concat(
                    "-fx-font-size: ", contigamepane.heightProperty().divide(30).asString(), "px; ",
                    "-fx-text-fill: black; ",
                    "-fx-font-family: 'Times New Roman'; ",
                    "-fx-font-weight: bold; ",
                    "-fx-font-style: italic;"));

            newgamPane.setTop(restarButton);
            BorderPane.setAlignment(restarButton, Pos.CENTER);
            Scene newgamescene = new Scene(newgamPane, 1200, 700);
            stage.setScene(newgamescene);

            restarButton.setOnMouseClicked(cl -> {
                BorderPane PausePane = new BorderPane();
                PausePage pausepage = new PausePage(stage, new BorderPane(startpane), origin, origionscene);
                PausePane.setCenter(pausepage);
                Scene pausesScene = new Scene(PausePane, 1200, 700);
                stage.setScene(pausesScene);

            });

            Scene newScene = new Scene(newgamPane, 1200, 700);
            stage.setScene(newScene);
        });

        homeImageView.setOnMouseClicked(ho -> {
            Scene homeScene = new Scene(startpane, 1200, 700);
            stage.setScene(homeScene);
        });

        continueImageView.setOnMouseClicked(co -> {

            Button restarButton = new Button("PAUSE");
            restarButton.styleProperty().bind(Bindings.concat(
                    "-fx-font-size: ", contigamepane.heightProperty().divide(30).asString(), "px; ",
                    "-fx-text-fill: black; ",
                    "-fx-font-family: 'Times New Roman'; ",
                    "-fx-font-weight: bold; ",
                    "-fx-font-style: italic;"));

            contigamepane.setTop(restarButton);
            BorderPane.setAlignment(restarButton, Pos.CENTER);

            stage.setScene(origionscene);

            restarButton.setOnMouseClicked(cl -> {
                BorderPane PausePane = new BorderPane();
                PausePage pausepage = new PausePage(stage, new BorderPane(startpane), origin, origionscene);
                PausePane.setCenter(pausepage);
                Scene pausesScene = new Scene(PausePane, 1200, 700);
                stage.setScene(pausesScene);

            });

            Scene cotinueScene = new Scene(contigamepane, 1200, 700);
            stage.setScene(cotinueScene);
        });
    }

    public void setWidth(double width) {
        super.setWidth(width);
        Pause();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        Pause();
    }

}

class eelsAndEscalator extends Pane {

    private Group escalator = new Group();
    private Group baGroup = new Group();
    private Group ButtonGroup = new Group();

    File file = new File("resources/GamePicture.jpg");
    Image gameImage = new Image(file.toURI().toString());
    ImageView gameImageView = new ImageView(gameImage);

    public eelsAndEscalator() {
        getChildren().addAll(baGroup, gameImageView, ButtonGroup);
        PlayEelsAndEscalator();

        // Bind the width and height properties to ensure dynamic resizing
        widthProperty().addListener((obs, oldVal, newVal) -> PlayEelsAndEscalator());
        heightProperty().addListener((obs, oldVal, newVal) -> PlayEelsAndEscalator());
    }

    public void PlayEelsAndEscalator() {

        escalator.getChildren().clear();
        baGroup.getChildren().clear();
        ButtonGroup.getChildren().clear();

        double hie = getHeight();
        double wid = getWidth();

        gameImageView.setLayoutX(wid * 0.05);
        gameImageView.setLayoutY(hie * 0.1);
        gameImageView.setFitWidth(wid * 0.9);
        gameImageView.setFitHeight(hie * 0.9);

        Rectangle backgroundRec = new Rectangle();
        backgroundRec.setWidth(wid * 2);
        backgroundRec.setHeight(hie * 2.2);
        backgroundRec.setFill(Color.WHITE);
        backgroundRec.setStroke(Color.BLACK);
        backgroundRec.setLayoutX(0);
        backgroundRec.setLayoutY(0);
        baGroup.getChildren().add(backgroundRec);
    }

    public void setWidth(double width) {
        super.setWidth(width);
        PlayEelsAndEscalator();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        PlayEelsAndEscalator();
    }

}

public class FinalProject extends Application {

    public void start(Stage arg0) {
        BorderPane Pane = new BorderPane();
        String StartGame = "CLICK TO START THE GAME";
        Label topicLabel = new Label(StartGame);
        topicLabel.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", Pane.heightProperty().divide(20).asString(), "px; ",
                "-fx-text-fill: red; ",
                "-fx-font-family: 'Times New Roman'; ",
                "-fx-font-weight: bold; ",
                "-fx-font-style: italic;"));

        File file = new File("resources/Background.jpg");
        Image startImage = new Image(file.toURI().toString());
        ImageView startImageView = new ImageView(startImage);
        startImageView.fitWidthProperty().bind(Pane.widthProperty());
        startImageView.fitHeightProperty().bind(Pane.heightProperty());

        Pane.getChildren().add(startImageView);
        Pane.setBottom(topicLabel);
        BorderPane.setAlignment(topicLabel, Pos.CENTER);

        Scene scene = new Scene(Pane, 1200, 700);
        Pane.setOnMouseClicked(ev -> {
            BorderPane gamePane = new BorderPane();
            eelsAndEscalator game = new eelsAndEscalator();
            gamePane.setCenter(game);

            Button restarButton = new Button("PAUSE");
            restarButton.styleProperty().bind(Bindings.concat(
                    "-fx-font-size: ", gamePane.heightProperty().divide(30).asString(), "px; ",
                    "-fx-text-fill: black; ",
                    "-fx-font-family: 'Times New Roman'; ",
                    "-fx-font-weight: bold; ",
                    "-fx-font-style: italic;"));

            gamePane.setTop(restarButton);
            BorderPane.setAlignment(restarButton, Pos.CENTER);
            Scene gamescene = new Scene(gamePane, 1200, 700);
            arg0.setScene(gamescene);

            restarButton.setOnMouseClicked(cl -> {
                BorderPane PausePane = new BorderPane();
                PausePage pausepage = new PausePage(arg0, new BorderPane(Pane), game, gamescene);
                PausePane.setCenter(pausepage);
                Scene pausesScene = new Scene(PausePane, 1200, 700);
                arg0.setScene(pausesScene);

            });
        });

        arg0.setTitle("EELS AND ESCALATOR");
        arg0.setScene(scene); // Place the scene in the stage
        arg0.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}

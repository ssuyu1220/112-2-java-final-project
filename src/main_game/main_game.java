package main_game;

import javafx.fxml.*;
import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class eelsAndEscalator extends Pane {

    private Group escalator = new Group();
    private Group baGroup = new Group();

    File file = new File("resources/GamePicture.jpg");
    Image gameImage = new Image(file.toURI().toString());
    ImageView gameImageView = new ImageView(gameImage);

    public eelsAndEscalator() {
        getChildren().addAll(baGroup, gameImageView);
        getChildren().addAll(steps.steps);
        PlayEelsAndEscalator();
    }

    public void PlayEelsAndEscalator() {

        escalator.getChildren().clear();
        baGroup.getChildren().clear();
        double hie = getHeight();
        double wid = getWidth();

        gameImageView.setLayoutX(wid * 0.05);
        gameImageView.setLayoutY(hie * 0.1);
        gameImageView.setFitWidth(wid * 0.9);
        gameImageView.setFitHeight(hie * 0.9);

        Rectangle bachgrounRec = new Rectangle();
        bachgrounRec.setWidth(wid * 2);
        bachgrounRec.setHeight(hie * 2.2);
        bachgrounRec.setFill(Color.WHITE);
        bachgrounRec.setStroke(Color.BLACK);
        bachgrounRec.setLayoutX(0);
        bachgrounRec.setLayoutY(0);
        baGroup.getChildren().add(bachgrounRec);

    }

    /*
     * public void restart() {
     * 
     * }
     */
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

public class main_game extends Application {
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
            // Pane.getChildren().clear();
            steps.creat_steps();
            eelsAndEscalator game = new eelsAndEscalator();
            // here
            Group g = new Group();
            Button reset = new Button("reset");

            reset.setOnAction(e -> {
                // Pane.getChildren().clear();
                game.setVisible(false);
                Pane.getChildren().remove(g);
            });
            Pane.setCenter(game);
            g.getChildren().addAll(reset, steps.dice);
            Pane.getChildren().add(g);
        });

        arg0.setTitle("EELS AND ESCALATOR");
        arg0.setScene(scene); // Place the scene in the stage
        arg0.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}

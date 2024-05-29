package main_game;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.*;

class RoulettePane extends Pane {
	private double radius = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
	private double centerX = getWidth() / 2;
	private double centerY = getHeight() / 2;

	// paint
	private void paintRoulette() {
		// Initialize roulette parameters
		getChildren().clear();
		radius = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
		centerX = getWidth() / 2;
		centerY = getHeight() / 2;
		// Draw circle
		Circle circle = new Circle(centerX, centerY, radius);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		getChildren().addAll(circle);
		// set 1 to 12
		for (int i = 0; i < 12; i++) {
			String num = new String();
			if (i % 6 == 0)
				num = "1";
			else if (i % 6 == 1)
				num = "//";
			else if (i % 6 == 2)
				num = "3";
			else if (i % 6 == 3)
				num = "?";
			else if (i % 6 == 4)
				num = "2";
			else
				num = "~";
			Text number = new Text(
					centerX + radius * 0.85 * Math.sin(Math.PI / 6 * (i )) - 4,
					centerY - radius * 0.85 * Math.cos(Math.PI / 6 * (i )) + 2,
					num);
			getChildren().addAll(number);
		}
		// draw blue lines
		for (int i = 0; i < 12; i++) {
			Line line = new Line(
					centerX, centerY,
					centerX + radius * Math.sin(Math.PI / 6 * (i + 1) - Math.PI / 12),
					centerY - radius * Math.cos(Math.PI / 6 * (i + 1) - Math.PI / 12));
			line.setStroke(Color.BLUE);
			getChildren().addAll(line);
		}
	}

	@Override
	public void setWidth(double width) {
		super.setWidth(width);
		paintRoulette();
	}

	@Override
	public void setHeight(double height) {
		super.setHeight(height);
		paintRoulette();
	}
}

public class tmp_dice {
	public static Stage dice_stage = new Stage();
	public static Button submit = new Button("Enter!");
	public static Label result = new Label();// Create a result text
	public static RoulettePane roulette = new RoulettePane();// Create a roulette

	public static void create_dice_stage() {
		// create top text
		Text redtop = new Text("TMP_DICE");
		redtop.setFill(Color.RED);
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);
		top.getChildren().add(redtop);

		BorderPane pane = new BorderPane();// Create a roulette
		HBox hbox = new HBox();// Create a hbox
		result.setVisible(false);
		// create and set button
		// set hbox(Text Field, button, result text)
		hbox.getChildren().addAll(submit, result);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(5);
		// set pane
		pane.setCenter(roulette);
		pane.setTop(top);
		pane.setBottom(hbox);
		// set red pointer
		Line line = new Line(roulette.getScaleX(), roulette.getScaleY(), 0, 0);
		line.setStroke(Color.RED);
		line.startXProperty().bind(roulette.widthProperty().divide(2));
		line.startYProperty().bind(roulette.heightProperty().add(32.5).divide(2));
		line.endXProperty().bind(roulette.widthProperty().divide(2));
		line.endYProperty().bind(roulette.heightProperty().divide(4));
		pane.getChildren().add(line);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 250);
		dice_stage.setTitle("roll tmp_dice"); // Set the stage title
		dice_stage.setScene(scene); // Place the scene in the stage
	}
	/*
	 * public static void main(String[] args) {
	 * launch(args);
	 * }
	 */
}

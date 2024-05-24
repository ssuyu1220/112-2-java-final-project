package three_coloring_puzzle_game;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.nio.file.Paths;

public class pane {
    public static BorderPane game_pane = new BorderPane();
    public static Stage tutorial_stage = new Stage();

    public static void create_game_pane() {
        create_tutorial();
        VBox bottom = new VBox();
        HBox assign_color = new HBox();
        assign_color.getChildren().addAll(controler.red, controler.green, controler.blue, controler.sure);
        assign_color.setAlignment(Pos.CENTER);
        assign_color.setSpacing(30);
        bottom.getChildren().addAll(controler.note, assign_color);
        game_pane.setBottom(bottom);
        HBox[] fills = new HBox[4];
        VBox root = new VBox();
        root.setSpacing(10);
        for (int i = 0; i < 4; i++) {
            fills[i] = new HBox();
            fills[i].setSpacing(10);
            fills[i].setAlignment(Pos.CENTER);
            for (int j = 0; j < 4; j++) {
                fills[i].getChildren().add(controler.fill[i * 4 + j]);
            }
            root.getChildren().addAll(fills[i]);
        }
        game_pane.setCenter(root);

        // set title
        HBox top = new HBox();
        Button tutorial = new Button("tutorial");
        tutorial.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tutorial_stage.show();
            }
        });
        Text redtop = new Text("3-Color Puzzle");
        top.getChildren().addAll(redtop, tutorial);
        top.setAlignment(Pos.CENTER);
        top.setSpacing(10);
        redtop.setFill(Color.RED);
        game_pane.setTop(top);
        controler.bot_play();
    }

    public static void create_tutorial() {
        BorderPane tutorial_pane = new BorderPane();
        Scene tutorial_scene = new Scene(tutorial_pane, 500, 500);
        tutorial_stage.setScene(tutorial_scene);
        Text top = new Text("Tutorial");
        Text content1 = new Text(
                "You play the game with Bot.\nYou and Bot take turns to assign color into white squares.\nYou need to choose a square at the center pane first, then choose the color at the bottom\nWhen assigning color, you need to follow the rules:");
        Text content2 = new Text(
                "1.The same color cannot share the same edge.\n2. Cannot assign the color that the previous player choose.");
        Text content3 = new Text("The one who cannot assign any color into any square lose.");
        VBox all = new VBox();

        HBox available_color = new HBox();
        available_color.setSpacing(5);
        Rectangle nex_red = new Rectangle(20, 20, Color.PINK);
        Rectangle nex_blue = new Rectangle(20, 20, Color.LIGHTBLUE);
        Rectangle nex_green = new Rectangle(20, 20, Color.LIGHTGREEN);
        Text available_color_text = new Text("The color which is light can be chose.");
        available_color.getChildren().addAll(nex_red, nex_green, nex_blue, available_color_text);

        HBox unavailable_color = new HBox();
        unavailable_color.setSpacing(5);
        Rectangle ex_red = new Rectangle(20, 20, Color.DARKRED);
        Rectangle ex_blue = new Rectangle(20, 20, Color.DARKBLUE);
        Rectangle ex_green = new Rectangle(20, 20, Color.DARKGREEN);
        Text unavailable_color_text = new Text("The color which is dark cannot be chose.");
        unavailable_color.getChildren().addAll(ex_red, ex_green, ex_blue, unavailable_color_text);

        HBox available_square = new HBox();
        available_square.setSpacing(5);
        Rectangle ex_available_square = new Rectangle(20, 20, Color.WHITE);
        ex_available_square.setStroke(Color.SILVER);
        ex_available_square.setStrokeWidth(2);
        Text available_square_text = new Text("The square with gray edges can be choose.");
        available_square.getChildren().addAll(ex_available_square, available_square_text);

        HBox unavailable_square = new HBox();
        unavailable_square.setSpacing(5);
        Rectangle ex_unavailable_square = new Rectangle(20, 20, Color.WHITE);
        ex_unavailable_square.setStroke(Color.BLACK);
        ex_unavailable_square.setStrokeWidth(2);
        Text unavailable_square_text = new Text("The square with black edges can be choose.");
        unavailable_square.getChildren().addAll(ex_unavailable_square, unavailable_square_text);

        all.getChildren().addAll(content1, content2, content3, available_square, unavailable_square, available_color,
                unavailable_color);
        all.setSpacing(15);
        tutorial_pane.setTop(top);
        tutorial_pane.setCenter(all);
    }

}

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static Rectangle[] fill = new Rectangle[16];
    public static boolean chose_pos = false;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        controler.build();
        pane.create_game_pane();
        // set scene
        Scene scene = new Scene(pane.game_pane, 400, 350);
        primaryStage.setTitle("3-Color Puzzle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

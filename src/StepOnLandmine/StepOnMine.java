package StepOnLandmine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StepOnMine extends Application {
    private static final int GRID_SIZE = 5;
    private Tile[][] grid;
    private List<Integer> minePositions;
    private int score;
    private Label scoreLabel;
    private boolean gameOver;

    @Override
    public void start(Stage primaryStage) {
        BorderPane Pane = new BorderPane();
        GridPane gameGrid = new GridPane(); // 遊戲的方格布局容器
        // 格子間的距離
        gameGrid.setHgap(10);
        gameGrid.setVgap(10);
        // 邊距
        gameGrid.setPadding(new Insets(10));

        scoreLabel = new Label("Score: 0");
        scoreLabel.styleProperty().bind(Bindings.concat(
                "-fx-font-size: ", Pane.heightProperty().divide(10).asString(), "px; ",
                "-fx-text-fill: red; ",
                "-fx-font-family: 'Times New Roman'; ",
                "-fx-font-weight: bold; ",
                "-fx-font-style: italic;"));
        Pane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(gameGrid);
        centerPane.setAlignment(Pos.CENTER);
        Pane.setRight(centerPane);

        resetGame(gameGrid);

        Scene scene = new Scene(Pane, 1200, 700);
        primaryStage.setTitle("Don't Step on Mine");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Bind grid size to window size
        gameGrid.prefWidthProperty().bind(scene.widthProperty().multiply(0.9));
        gameGrid.prefHeightProperty().bind(scene.heightProperty().multiply(0.9));
    }

    private void resetGame(GridPane gameGrid) {
        score = 0;
        gameOver = false;
        // 更新分數
        updateScoreLabel();
        // 生成地雷位置
        minePositions = generateMinePositions();
        grid = new Tile[GRID_SIZE][GRID_SIZE];
        gameGrid.getChildren().clear();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Tile tile = new Tile(row, col);
                tile.getRectangle().setFill(Color.WHITE);
                tile.getRectangle().setStroke(Color.BLACK);
                grid[row][col] = tile;
                // 增加方格到GridPane
                gameGrid.add(tile.getRectangle(), col, row);

                tile.getRectangle().setOnMouseClicked(cl -> handleTileClick(tile));
            }
        }

        // Bind each rectangle's size to the game grid size
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Tile eachtile = grid[row][col];// 取得方格
                eachtile.getRectangle().widthProperty()
                        .bind(gameGrid.prefWidthProperty().divide(GRID_SIZE).subtract(20));
                eachtile.getRectangle().heightProperty()
                        .bind(gameGrid.prefHeightProperty().divide(GRID_SIZE).subtract(20));
            }
        }
    }

    // 避免重複計分
    private void handleTileClick(Tile tile) {
        if (gameOver || tile.isClicked()) {
            return; // If the game is over or the tile has been clicked, do nothing
        }

        tile.setClicked(true); // 設為點擊過

        if (minePositions.contains(tile.getRow() * GRID_SIZE + tile.getCol())) { // 如果點到地雷
            tile.getRectangle().setFill(Color.RED);
            showGameOver();
            gameOver = true;
        } else { // 如果不是點到地雷
            tile.getRectangle().setFill(Color.GREEN);
            score++;
            updateScoreLabel(); // 更新分數
            checkGameWon(); // 確認是否贏了
        }
    }

    // 更新分數
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void showGameOver() {
        scoreLabel.setText("Game Over! Score: " + score);
    }

    private void checkGameWon() {
        int totalTiles = GRID_SIZE * GRID_SIZE;
        int tilesClicked = score + minePositions.size();
        if (tilesClicked == totalTiles) {
            scoreLabel.setText("You Won! Score: " + score);
            gameOver = true;
        }
    }

    private List<Integer> generateMinePositions() {
        List<Integer> positions = new ArrayList<>();
        Random random = new Random();
        // 生成兩個地雷位置
        while (positions.size() < 3) {
            int position = random.nextInt(GRID_SIZE * GRID_SIZE);
            // 避免位置重複
            // .contains()用來檢查是否一樣
            if (!positions.contains(position)) {
                positions.add(position);
            }
        }

        return positions;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Tile {
        private final int row;
        private final int col;
        private final Rectangle rectangle;
        private boolean clicked;

        public Tile(int row, int col) {
            this.row = row;
            this.col = col;
            this.rectangle = new Rectangle();
            this.clicked = false;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public Rectangle getRectangle() {
            return rectangle;
        }

        public boolean isClicked() {
            return clicked;
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
        }
    }
}

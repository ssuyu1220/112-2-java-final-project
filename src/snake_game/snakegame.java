package snake_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class snakegame extends Application {
    // variable
    static int speed = 5;
    static int foodcolor = 0;
    static int width = 48;
    static int height = 28;
    static int foodX = 0;
    static int foodY = 0;
    static int squaresize = 25;
    static int score = 0;
    static List<square> snake1 = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();

    // 列舉蛇的方向
    public enum Dir {
        left, right, up, down
    }

    // 表示蛇的位置
    public static class square {
        int x;
        int y;

        public square(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void start(Stage arg0) {
        newFood();

        VBox vbox = new VBox();
        Canvas canvas = new Canvas(width * squaresize, height * squaresize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        vbox.getChildren().addAll(canvas);

        // 每隔一段時間重新繪製
        new AnimationTimer() {
            long lastPlay = 0;

            public void handle(long now) {
                if (lastPlay == 0) {
                    lastPlay = now;
                    Play(gc);
                    return;
                }

                if (now - lastPlay > 1000000000 / speed) { // 每過 1秒/速度 便重新繪製
                    lastPlay = now;
                    Play(gc);
                }
            }
        }.start();

        Scene scene = new Scene(vbox, width * squaresize, height * squaresize);

        // 用方向鍵決定蛇的方向
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.UP) {
                direction = Dir.up;
            }
            if (key.getCode() == KeyCode.LEFT) {
                direction = Dir.left;
            }
            if (key.getCode() == KeyCode.DOWN) {
                direction = Dir.down;
            }
            if (key.getCode() == KeyCode.RIGHT) {
                direction = Dir.right;
            }
        });

        // 設置蛇最一開始的位置
        snake1.add(new square(width / 2, height / 2));
        snake1.add(new square(width / 2, height / 2));
        snake1.add(new square(width / 2, height / 2));

        arg0.setScene(scene);
        arg0.setTitle("SNAKE GAME");
        arg0.show();

    }

    // Play
    public static void Play(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Times New Roman", 50));
            gc.fillText("GAME OVER", width * squaresize * 0.38, height * squaresize * 0.45);
            return;
        }
        // 除了頭,將剩餘的身體往前一格身體放
        for (int i = snake1.size() - 1; i >= 1; i--) {
            snake1.get(i).x = snake1.get(i - 1).x;
            snake1.get(i).y = snake1.get(i - 1).y;
        }

        switch (direction) { // 依照方向畫蛇頭位置
            case up:
                snake1.get(0).y--;
                if (snake1.get(0).y < 0) { // 如果碰壁則死
                    gameOver = true;
                }
                break;
            case down:
                snake1.get(0).y++;
                if (snake1.get(0).y >= height) { // 如果碰壁則死
                    gameOver = true;
                }
                break;
            case left:
                snake1.get(0).x--;
                if (snake1.get(0).x < 0) { // 如果碰壁則死
                    gameOver = true;
                }
                break;
            case right:
                snake1.get(0).x++;
                if (snake1.get(0).x >= width) { // 如果碰壁則死
                    gameOver = true;
                }
                break;
        }

        // 吃
        if (foodX == snake1.get(0).x && foodY == snake1.get(0).y) {
            snake1.add(new square(-1, -1));
            score++;
            newFood(); // 畫新食物
        }

        // 自爆
        for (int i = 1; i < snake1.size(); i++) {
            if (snake1.get(0).x == snake1.get(i).x && snake1.get(0).y == snake1.get(i).y) { // 如果頭撞到身體
                gameOver = true;
            }
        }
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * squaresize, height * squaresize);
        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Times New Roman", 30));
        gc.fillText("Score: " + score, 10, 30);

        // 隨機食物顏色
        Color cc = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.PURPLE;
                break;
            case 1:
                cc = Color.LIGHTBLUE;
                break;
            case 2:
                cc = Color.YELLOW;
                break;

        }
        // 畫食物
        gc.setFill(cc);
        gc.fillOval(foodX * squaresize, foodY * squaresize, squaresize, squaresize);

        // 畫蛇蛇
        for (square snakeBody : snake1) {
            gc.setFill(Color.GREEN);
            gc.fillRect(snakeBody.x * squaresize, snakeBody.y * squaresize, squaresize - 1, squaresize - 1); // 保留點空間不然看起來像黏在一起
        }
    }

    // 決定食物的位置及顏色
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (square snakeBody : snake1) {
                if (snakeBody.x == foodX && snakeBody.y == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(3);
            speed++;
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

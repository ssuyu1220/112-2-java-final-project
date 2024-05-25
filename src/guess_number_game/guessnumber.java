package guess_number_game;

import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class guessnumber extends Application {
    static Font fnt = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 20);
    static Font fnt1 = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 15);
    static Font fnt2 = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 18);
    static Font fnt3 = Font.font("PLAIN", FontWeight.BOLD, FontPosture.REGULAR, 23);
    static BorderPane pane = new BorderPane();
    static Button[] numbtn = new Button[10];
    static Button clearbtn = new Button("CLEAR");
    static Button exitbtn = new Button("EXIT*");
    static Button enterbtn = new Button("ENTER");
    static VBox sgt = new VBox(); // 紀錄
    static Button[] guessbtn = new Button[4]; // [0] [1] [2] [3]
    static Label[] guessnum = new Label[4];
    StringBuffer mynum = new StringBuffer("0000"); // 顯示猜的數字
    public static int select = 0; // 被選取的格子
    public static int times = 0;
    static Label timeslb = new Label("0 try");
    static Label result = new Label(""); // A B
    static Label wrongInput = new Label("");
    static Label winLose = new Label("");
    public static int A = 0;
    public static int B = 0;
    public static boolean bool = true; // 是否有猜中

    public int randomnum() {
        while (true) {
            int n = (int) (Math.random() * 9876) + 1;
            int tmp = n;
            HashSet<Integer> nh = new HashSet<Integer>();
            while (n > 0) {
                nh.add(n % 10);
                n /= 10;
                if (n == 0 && tmp < 1000) {
                    nh.add(n);
                }
            }
            System.out.println(nh);
            if (nh.size() == 4) {
                return tmp;
            }
        }
    }

    public boolean valid(int n) {
        HashSet<Integer> nh = new HashSet<Integer>();
        int tmp = n;
        while (n > 0) {
            nh.add(n % 10);
            n /= 10;
            if (n == 0 && tmp < 1000) {
                nh.add(n);
            }
        }
        // System.out.println(nh);
        if (nh.size() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public void start(Stage primaryStage) {
        String target = String.format("%04d", randomnum());
        System.out.println(target);
        Group g = new Group();

        // setGuess
        HBox fourbit = new HBox();
        for (int i = 0; i < 4; i++) {
            guessbtn[i] = new Button("");
            guessbtn[i].setPrefHeight(45);
            guessbtn[i].setPrefWidth(45);
            guessbtn[i].setFont(fnt);
            guessbtn[i].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
            fourbit.getChildren().add(guessbtn[i]);
            int tmp = i;
            guessbtn[i].setOnAction(e -> {
                guessbtn[select].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
                select = tmp;
                guessbtn[tmp].setStyle("-fx-background-color: #d8e2f9 ; -fx-border-color: #393939");
                // System.out.println(select);
            });
        }
        guessbtn[select].setStyle("-fx-background-color: #d8e2f9 ; -fx-border-color: #393939");
        fourbit.setSpacing(2);
        fourbit.setAlignment(Pos.CENTER);

        // setNumberButton
        HBox zeroToFour = new HBox();
        HBox fiveToNine = new HBox();
        VBox rows = new VBox();
        Group numberBtn = new Group();
        for (int i = 0; i < 10; i++) {
            numbtn[i] = new Button("" + i);
            numbtn[i].setPrefHeight(45);
            numbtn[i].setPrefWidth(45);
            numbtn[i].setFont(fnt);
            numbtn[i].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
            if (i < 5) {
                zeroToFour.getChildren().add(numbtn[i]);
            } else {
                fiveToNine.getChildren().add(numbtn[i]);
            }
            int tmp = i;
            numbtn[i].setOnAction(e -> {
                guessbtn[select].setText("" + tmp);
                guessbtn[select].setFont(fnt);
                mynum.setCharAt(select, (char) (tmp + '0'));
                if (select < 3) {
                    guessbtn[select].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
                    select++;
                }
                guessbtn[select].setStyle("-fx-background-color: #d8e2f9 ; -fx-border-color: #393939");
            });
        }
        zeroToFour.setSpacing(5);
        fiveToNine.setSpacing(5);
        rows.getChildren().addAll(zeroToFour, fiveToNine);
        rows.setAlignment(Pos.TOP_CENTER);
        rows.setSpacing(5);
        numberBtn.getChildren().add(rows);

        // enter & clear && exit
        winLose.setFont(fnt3);
        timeslb.setFont(fnt2);
        wrongInput.setFont(fnt1);
        result.setFont(fnt2);
        VBox toplb = new VBox();
        VBox tools = new VBox();
        sgt.getChildren().add(new Label("    "));
        enterbtn.setFont(fnt1);
        enterbtn.setStyle("-fx-background-color: #ffe047 ; -fx-border-color: #393939");
        enterbtn.setOnAction(e -> {
            wrongInput.setText("");
            boolean check = true && valid(Integer.valueOf(mynum.toString()));
            // 沒有輸入
            for (int i = 0; i < 4; i++) {
                if (guessbtn[i].getText().equals("")) {
                    check = false;
                    break;
                }
            }
            if (check && times <= 9 && bool) {
                times++;
                timeslb.setText(times + " try");
                // System.out.println(mynum);
                String mine = mynum.toString();
                // count A
                A = 0;
                B = 0;
                for (int i = 0; i < 4; i++) {
                    if (mine.charAt(i) == target.charAt(i)) {
                        A++;
                    }
                }
                // count B
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (mine.charAt(j) == target.charAt(i) && i != j) {
                            B++;
                        }
                    }
                }
                if (A == 4 && B == 0) {
                    bool = false;
                    winLose.setText("超強!!加分!");
                }
                result.setText(A + "A" + B + "B");
                // 紀錄
                sgt.getChildren().addAll(new Label("  # " + times), new Label("  " + A + " A " + B + " B"),
                        new Label("  " + mine));
                System.out.println(A + " " + B);
            } else if (check && times > 9 && bool) {
                winLose.setText("猜不到吧哈哈哈哈");
                timeslb.setTextFill(Color.INDIANRED);
            } else if (bool) {
                wrongInput.setText("Please Check Your Number!!!");
                wrongInput.setTextFill(Color.INDIANRED);
            }
            guessbtn[select].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
            select = 0;
            guessbtn[select].setStyle("-fx-background-color: #d8e2f9 ; -fx-border-color: #393939");
        });
        // exit
        exitbtn.setFont(fnt1);
        exitbtn.setStyle("-fx-background-color: #d1ff87 ; -fx-border-color: #393939");
        exitbtn.setOnAction(e -> {
            primaryStage.close();
            // System.out.println("exit");
        });
        // clear
        clearbtn.setFont(fnt1);
        clearbtn.setStyle("-fx-background-color: #ececec ; -fx-border-color: #393939");
        clearbtn.setOnAction(e -> {
            for (int i = 0; i < 4; i++) {
                guessbtn[i].setText("");
            }
            guessbtn[select].setStyle("-fx-background-color: #ffffff ; -fx-border-color: #393939");
            select = 0;
            guessbtn[select].setStyle("-fx-background-color: #d8e2f9 ; -fx-border-color: #393939");
        });
        tools.setSpacing(10);
        tools.getChildren().addAll(enterbtn, clearbtn, exitbtn);
        tools.setAlignment(Pos.CENTER);
        toplb.getChildren().addAll(new Label("    "), timeslb, result, wrongInput, winLose);
        toplb.setAlignment(Pos.CENTER);
        toplb.setSpacing(5);
        VBox v1 = new VBox();
        v1.getChildren().addAll(toplb, fourbit, rows);
        v1.setSpacing(100);
        g.getChildren().add(v1);

        // 紀錄
        Group rcd = new Group();
        Rectangle rec = new Rectangle(100, 500);
        rec.setFill(Color.WHITE);
        rec.setStroke(Color.web("#cecece"));
        Label lbl = new Label(" -RECORD-");
        lbl.setFont(fnt2);
        lbl.setAlignment(Pos.CENTER);
        rcd.getChildren().addAll(rec, lbl, sgt);

        // pane
        HBox hb = new HBox();
        hb.getChildren().addAll(rcd, g, tools);
        hb.setSpacing(65);
        Group g2 = new Group();
        g2.getChildren().add(hb);
        pane.setCenter(g2);

        // scene
        Scene scene = new Scene(pane, 550, 510);
        primaryStage.setTitle("1A2B!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}

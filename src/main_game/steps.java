package main_game;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import java.util.*;

import guess_number_game.*;
import three_coloring_puzzle_game.*;

class dice_handler implements EventHandler<ActionEvent> {
    private int lucky = 0;
    ///
    private int once = 0;
    private int climb = 0;
    private int slide=0;
    public static Circle moving = new Circle(6.0);
    public static int winner;
    @Override
    public void handle(ActionEvent e) {
        tmp_dice.result.setText("");
        tmp_dice.create_dice_stage();
        tmp_dice.dice_stage.show();
        tmp_dice.submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("cur"+steps.cur_player);
                once++;
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), tmp_dice.roulette);
                rotateTransition.setFromAngle(0);// restart from 0
                if (once <= 1) {
                    lucky = (int) (Math.random() * 12) + 1;
                    rotateTransition.setByAngle(360 + 30 * (12 - lucky)); // rotate 360 + 30 * (12 - lucky)
                    rotateTransition.setAutoReverse(false); // 不自動反轉
                    // start to rotate
                    rotateTransition.play();
                    // display result text
                    String roll = new String();
                    lucky = lucky % 6+1;    
                    System.out.println("lucky"+lucky);
                    if (lucky == 1) {
                        roll = "go " + 1 + " step";
                    } else if (lucky == 5 ) {
                        roll = "go " + 2 + " steps";
                    }
                    else if(lucky == 3){
                        roll = "go " + 3 + " steps";
                    }
                    else if (lucky == 2) {
                        switch (steps.players_pos[steps.cur_player]) {
                            case 0:
                                roll = "climb ladder";
                                climb = 1;
                                break;
                            case 10:
                                roll = "climb ladder";
                                climb = 3;
                                break;
                            case 4:
                                roll = "climb ladder";
                                climb = 2;
                                break;
                            default:
                                once = 0;
                                roll = "spin again";
                                break;
                        }
                    } else if (lucky == 6) {
                        switch(steps.players_pos[steps.cur_player]){
                            case 4:
                                roll ="eel";
                                slide=1;
                                break;
                            case 13:
                                roll ="eel";
                                slide=2;
                                break;
                            case 17:
                                roll ="eel";
                                slide=3;
                                break;
                            case 18:
                                roll ="eel";
                                slide=4;
                                break;
                            default:
                                once = 0;
                                roll ="spin again";
                                break;
                        }
                    } else {
                        roll = "play secreat game";
                        int secretGame = (int) (Math.random() * 3) + 1;
                        // paulse for 3 sec.
                        PauseTransition delay = new PauseTransition(Duration.seconds(2));
                        delay.setOnFinished(event -> {
                            Stage stage = new Stage();
                            if (secretGame == 1) {
                                new guessnumber().start(stage);
                            } else if (secretGame == 2) {
                                new three_coloring_puzzle_game().start(stage);
                            }
                            stage.show();
                        });
                        delay.play();
                        System.out.println(secretGame);
                    }
                    System.out.println(lucky);
                    tmp_dice.result.setText(String.format("Result : %s", roll));
                    tmp_dice.result.setVisible(true);
                }
            }
        });
        tmp_dice.dice_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                if (lucky == 2) {
                    int aim = 0;
                    System.out.println(steps.players_pos[steps.cur_player]+"climb"+climb);
                    Color color = steps.player_color[steps.cur_player];
                    moving.setFill(color);
                    moving.setVisible(true);
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), moving);
                    switch (climb) {
                        case 1: // 1-10
                            aim = 10;
                            translateTransition.setFromX(390.0);
                            translateTransition.setFromY(495.0);
                            translateTransition.setToX(525.0);
                            translateTransition.setToY(340.0);
                            steps.sliding();
                            translateTransition.play();
                            break;
                        case 2: // 5-8
                            aim = 8;
                            translateTransition.setFromX(900.0);
                            translateTransition.setFromY(495.0);
                            translateTransition.setToX(760.0);
                            translateTransition.setToY(345.0);
                            steps.sliding();
                            translateTransition.play();
                            break;
                        case 3: // 11-14
                            aim = 14;
                            translateTransition.setFromX(400.0);
                            translateTransition.setFromY(340.0);
                            translateTransition.setToX(260.0);
                            translateTransition.setToY(190.0);
                            steps.sliding();
                            translateTransition.play();
                            break;
                    }
                    translateTransition.setCycleCount(1);
                    translateTransition.setAutoReverse(false);
                    steps.players_pos[steps.cur_player] = aim;
                    translateTransition.setOnFinished(e1 -> {
                        steps.set_pos_visible();
                        moving.setVisible(false);
                        steps.win();
                        steps.cur_player = (steps.cur_player + 1) % 4;
                    });
                } 
                else if(lucky==6){
                    Color color=steps.player_color[steps.cur_player];
                    switch(slide){
                        //溜出去會回到原點
                        case 1: //5
                            moving.setFill(color);
                            moving.setVisible(true);
                            moving.setLayoutX(750.0);
                            moving.setLayoutY(550.0);
                            steps.eel[0].setRotate(165.0);
                            PathTransition ani1=new PathTransition(Duration.millis(1000),steps.eel[0],moving);
                            ani1.setCycleCount(1);
                            ani1.play();
                            steps.sliding();
                            ani1.setOnFinished(e4->{
                                moving.setVisible(false);
                                steps.players_pos[steps.cur_player]=0;
                                steps.win();
                                steps.set_pos_visible();
                                steps.cur_player = (steps.cur_player + 1) % 4;
                            });
                            break;
                        case 2: //13
                            moving.setFill(color);
                            moving.setVisible(true);
                            moving.setLayoutX(255.0);
                            moving.setLayoutY(360.0);
                            steps.eel[1].setRotate(50.0);
                            PathTransition ani2=new PathTransition(Duration.millis(1000),steps.eel[1],moving);
                            ani2.setCycleCount(1);
                            ani2.play();
                            steps.sliding();
                            ani2.setOnFinished(e4->{
                                moving.setVisible(false);
                                steps.players_pos[steps.cur_player]=0;
                                steps.win();
                                steps.set_pos_visible();
                                steps.cur_player = (steps.cur_player + 1) % 4;
                            });
                            break;
                        case 3: //11-14
                            moving.setFill(color);
                            moving.setVisible(true);
                            moving.setLayoutX(490.0);
                            moving.setLayoutY(140.0);
                            steps.eel[2].setRotate(195.0);
                            PathTransition ani3=new PathTransition(Duration.millis(1000),steps.eel[2],moving);
                            ani3.setCycleCount(1);
                            ani3.play();
                            steps.sliding();
                            ani3.setOnFinished(e4->{
                                moving.setVisible(false);
                                steps.players_pos[steps.cur_player]=0;
                                steps.win();
                                steps.set_pos_visible();
                                steps.cur_player = (steps.cur_player + 1) % 4;
                            });
                            break;
                        case 4:
                            moving.setFill(color);
                            moving.setVisible(true);
                            moving.setLayoutX(700.0);
                            moving.setLayoutY(350.0);
                            steps.eel[3].setRotate(-250.0);
                            PathTransition ani4=new PathTransition(Duration.millis(1000),steps.eel[3],moving);
                            ani4.setCycleCount(1);
                            ani4.play();
                            steps.sliding();
                            ani4.setOnFinished(e4->{
                                steps.players_pos[steps.cur_player]=3;
                                steps.win();
                                steps.set_pos_visible();
                                moving.setVisible(false);
                                steps.cur_player = (steps.cur_player + 1) % 4;
                            }); 
                            break;
                    }
                }
                else if(lucky==5){
                    steps.players_pos[steps.cur_player] += 2;
                    steps.win();
                    steps.set_pos_visible();
                    steps.cur_player = (steps.cur_player + 1) % 4;
                }
                else {
                    steps.players_pos[steps.cur_player] += lucky;
                    steps.win();
                    steps.set_pos_visible();
                    steps.cur_player = (steps.cur_player + 1) % 4;
                }
                lucky = 0;
                once = 0;
            }
        });
    }
}

public class steps {
    public static Group[] steps = new Group[20];
    public static Group ladders = new Group(); // ladders
    public static Circle[][] player_sym = new Circle[20][4];
    public static Button dice = new Button("dice");
    public static int cur_player = 0;
    public static int[] players_pos = { 0, 0, 0, 0 };
    public static Color[] player_color = { Color.DARKSLATEGRAY, Color.DARKCYAN, Color.DARKGREY, Color.DARKMAGENTA };
    public static Polyline[] eel=new Polyline[4]; //eel
    public static Group[] eels=new Group[4];
    public static void create_ladders() {
        Rectangle[] basic = new Rectangle[3];
        for (int i = 0; i < 3; i++) {
            basic[i] = new Rectangle(70, 200, Color.TRANSPARENT);
            // basic[i].setStroke(Color.WHEAT);
        }
        basic[0].setLayoutX(365.0);
        basic[0].setLayoutY(190.0);
        basic[0].setRotate(-37);
        basic[1].setLayoutX(830.0);
        basic[1].setLayoutY(345.0);
        basic[1].setRotate(-40);
        basic[2].setLayoutX(445.0);
        basic[2].setLayoutY(340.0);
        basic[2].setRotate(47);
        ladders.getChildren().addAll(basic);
        set_pos_visible();
    }
    public static void create_eels(){
        eel[0]=new Polyline();
        eel[0].setFill(Color.TRANSPARENT);
        eels[0]=new Group();
        ObservableList<Double> eelsList0;
        eelsList0=eel[0].getPoints();
        for(int x=-170;x<=70;x++){
            eelsList0.add(x+100.0);
            eelsList0.add(-10*Math.sin((x/100.0)*2*Math.PI));
        }
        eels[0].getChildren().addAll(eel[0]);
        eels[0].setLayoutX(750.0);
        eels[0].setLayoutY(570.0);
        eels[0].setRotate(-30.0);

        eel[1]=new Polyline();
        eel[1].setFill(Color.TRANSPARENT);
        eels[1]=new Group();
        ObservableList<Double> eelsList1;
        eelsList1=eel[1].getPoints();
        for(int x=-160;x<=70;x++){
            eelsList1.add(x+100.0);
            eelsList1.add(-10*Math.sin((x/100.0)*2*Math.PI));
        }
        eels[1].getChildren().addAll(eel[1]);
        eels[1].setLayoutX(255.0);
        eels[1].setLayoutY(360.0);
        eels[1].setRotate(50.0);

        eel[2]=new Polyline();
        eel[2].setFill(Color.TRANSPARENT);
        eels[2]=new Group();
        ObservableList<Double> eelsList2;
        eelsList2=eel[2].getPoints();
        for(int x=-250;x<=70;x++){
            eelsList2.add(x+100.0);
            eelsList2.add(-5*Math.sin((x/100.0)*2*Math.PI));
        }
        eels[2].getChildren().addAll(eel[2]);
        eels[2].setLayoutX(490.0);
        eels[2].setLayoutY(145.0);
        eels[2].setRotate(15.0);

        eel[3]=new Polyline();
        eel[3].setFill(Color.TRANSPARENT);
        eels[3]=new Group();
        ObservableList<Double> eelsList3;
        eelsList3=eel[3].getPoints();
        for(int x=-250;x<=70;x++){
            eelsList3.add(x+100.0);
            eelsList3.add(-10*Math.sin((x/100.0)*2*Math.PI));
        }
        eels[3].getChildren().addAll(eel[3]);
        eels[3].setLayoutX(700.0);
        eels[3].setLayoutY(350.0);
        eels[3].setRotate(-70.0);
    }
    public static void creat_steps() {
        dice.setLayoutX(60);
        dice.setOnAction(new dice_handler());
        Rectangle[] back = new Rectangle[20];
        HBox[] players_box = new HBox[20];
        for (int i = 0; i < 20; i++) {
            players_box[i] = new HBox();
            players_box[i].setSpacing(5);
            for (int j = 0; j < 4; j++) {
                player_sym[i][j] = new Circle(6.0, player_color[j]);
                players_box[i].getChildren().add(player_sym[i][j]);
            }
            steps[i] = new Group();
            back[i] = new Rectangle(70, 30, Color.GOLD);
            steps[i].getChildren().addAll(back[i], players_box[i], new Label(Integer.toString(i)));
        }
        steps[0].setLayoutX(265.0);
        steps[0].setLayoutY(495.0);
        steps[1].setLayoutX(390.0);
        steps[1].setLayoutY(495.0);
        steps[2].setLayoutX(510.0);
        steps[2].setLayoutY(495.0);
        steps[3].setLayoutX(630.0);
        steps[3].setLayoutY(495.0);
        steps[4].setLayoutX(755.0);
        steps[4].setLayoutY(495.0);
        steps[5].setLayoutX(900.0);
        steps[5].setLayoutY(495.0);
        steps[6].setLayoutX(930.0);
        steps[6].setLayoutY(415.0);
        steps[7].setLayoutX(915.0);
        steps[7].setLayoutY(355.0);
        steps[8].setLayoutX(760.0);
        steps[8].setLayoutY(345.0);
        steps[9].setLayoutX(645.0);
        steps[9].setLayoutY(340.0);
        steps[10].setLayoutX(525.0);
        steps[10].setLayoutY(340.0);
        steps[11].setLayoutX(400.0);
        steps[11].setLayoutY(340.0);
        steps[12].setLayoutX(250.0);
        steps[12].setLayoutY(330.0);
        steps[13].setLayoutX(235.0);
        steps[13].setLayoutY(255.0);
        steps[14].setLayoutX(260.0);
        steps[14].setLayoutY(190.0);
        steps[15].setLayoutX(400.0);
        steps[15].setLayoutY(180.0);
        steps[16].setLayoutX(520.0);
        steps[16].setLayoutY(180.0);
        steps[17].setLayoutX(640.0);
        steps[17].setLayoutY(180.0);
        steps[18].setLayoutX(760.0);
        steps[18].setLayoutY(180.0);
        steps[19].setLayoutX(890.0);
        steps[19].setLayoutY(180.0);
        set_pos_visible();
    }

    public static void set_pos_visible() {
        for (int i = 0; i < 20; i++) {
            if (players_pos[0] == i)
                player_sym[i][0].setVisible(true);
            else
                player_sym[i][0].setVisible(false);
            if (players_pos[1] == i)
                player_sym[i][1].setVisible(true);
            else
                player_sym[i][1].setVisible(false);
            if (players_pos[2] == i)
                player_sym[i][2].setVisible(true);
            else
                player_sym[i][2].setVisible(false);
            if (players_pos[3] == i)
                player_sym[i][3].setVisible(true);
            else
                player_sym[i][3].setVisible(false);
        }
    }
    public static void sliding() {
        player_sym[players_pos[cur_player]][cur_player].setVisible(false);
    }
    public static void game_data_init() {
        cur_player = 0;
        players_pos[0] = 0;
        players_pos[1] = 0;
        players_pos[2] = 0;
        players_pos[3] = 0;

    }
    public static void win(){
        Font fnt2 = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 48);
        Label winlb=new Label("WIN!!!\nWIN!!!");
        Label sgtlb=new Label("click to restart");
        Button clickbtn=new Button("click");
        BorderPane winPane=new BorderPane();
        if(players_pos[cur_player]>=19){
            players_pos[cur_player]=19;
            player_sym[19][cur_player].setVisible(true);
            Stage pop=new Stage();
            winlb.setFont(fnt2);
            winPane.setCenter(winlb);
            VBox vb=new VBox();
            vb.getChildren().addAll(sgtlb,clickbtn);
            vb.setAlignment(Pos.CENTER);
            winPane.setBottom(vb);
            Scene winScene=new Scene(winPane,500,300);
            pop.setScene(winScene);
            pop.show();
            clickbtn.setOnAction(e->{
                pop.close();
                main_game.main_stage.setScene(main_game.gamescene);
                game_data_init();
                set_pos_visible();
            });
        }
        else{
            return;
        }
    }
}

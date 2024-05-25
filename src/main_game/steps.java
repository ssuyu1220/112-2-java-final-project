package main_game;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import java.util.*;

import guess_number_game.*;
import three_coloring_puzzle_game.*;

class dice_handler implements EventHandler<ActionEvent> {
    private int lucky = 0;
    ///
    private int once=0;
    private int climb=0;
    public static Circle moving=new Circle(6.0);
    @Override
    public void handle(ActionEvent e) {
        tmp_dice.result.setText("");
        tmp_dice.create_dice_stage();
        tmp_dice.dice_stage.show();
        tmp_dice.submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                ///
                once++;
                if(once<=1){
                    lucky = (int) (Math.random() * 12) + 1;
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), tmp_dice.roulette);
                    rotateTransition.setFromAngle(0);// restart from 0
                    rotateTransition.setByAngle(360 + 30 * (12 - lucky)); // rotate 360 + 30 * (12 - lucky)
                    rotateTransition.setAutoReverse(false); // 不自動反轉
                    // start to rotate
                    rotateTransition.play();
                    // display result text
                    String roll = new String();
                    lucky =5;// lucky % 6 + 1;
                    if (lucky == 1){
                        roll = "go " + Integer.toString(lucky) + " step";
                    }
                    else if (lucky < 4){
                        roll = "go " + Integer.toString(lucky) + " steps";
                    }
                    else if(lucky == 5){
                        /*
                         * 我先設轉到梯子並在梯子前一格才能爬梯子
                         * 如果不符合條件則重轉
                         */
                        switch(steps.players_pos[steps.cur_player]){
                            case 0:
                                roll ="climb ladder";
                                climb=1;
                                break;
                            case 10:
                                roll ="climb ladder";
                                climb=3;
                                break;
                            case 4:
                                roll ="climb ladder";
                                climb=2;
                                break;
                            default:
                                once=0;
                                roll ="spin again";
                                break;
                        }
                    }
                    else if(lucky == 6){
                        roll="eel";
                    }
                    else{
                        roll = "play secreat game";
                        int secretGame=(int) (Math.random() * 3) + 1;
                        //paulse for 3 sec.
                        PauseTransition delay = new PauseTransition(Duration.seconds(3));
                        delay.setOnFinished(event ->{
                            Stage stage=new Stage();
                            if (secretGame==1){
                                new guessnumber().start(stage);
                            }
                            else if(secretGame==2){
                                new Main().start(stage);
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
                if(lucky==5 || lucky==6){
                    int aim=0;
                    Color color=steps.player_color[steps.cur_player];
                    moving.setFill(color);
                    moving.setVisible(true);
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), moving);
                    switch(climb){
                        case 1: //1-10
                            aim=10;
                            translateTransition.setFromX(390.0);
                            translateTransition.setFromY(495.0);
                            translateTransition.setToX(525.0);
                            translateTransition.setToY(340.0);
                            translateTransition.play();
                            break;
                        case 2: //5-8
                            aim=8;
                            translateTransition.setFromX(390.0);
                            translateTransition.setFromY(495.0);
                            translateTransition.setToX(760.0);
                            translateTransition.setToY(345.0);
                            translateTransition.play();
                            break;
                        case 3: //11-14
                            aim=14;
                            translateTransition.setFromX(400.0);
                            translateTransition.setFromY(340.0);
                            
                            translateTransition.setToX(260.0);
                            translateTransition.setToY(190.0);
                            translateTransition.play();
                            break;
                    }
                    translateTransition.setCycleCount(1); 
                    translateTransition.setAutoReverse(false);
                    steps.players_pos[steps.cur_player]=aim;
                    translateTransition.setOnFinished(e1->{
                        steps.cur_player = (steps.cur_player + 1) % 4;
                        steps.set_pos_visible();
                        moving.setVisible(false);
                    });
                    
                }
                else{
                    steps.players_pos[steps.cur_player] += lucky;
                    steps.cur_player = (steps.cur_player + 1) % 4;
                    steps.set_pos_visible();
                }
                lucky=0;
                once=0;
            }
        });
    }
}

public class steps {
    public static Group[] steps = new Group[20];
    public static Group ladders=new Group(); //ladders
    public static Circle[][] player_sym = new Circle[20][4];
    public static Button dice = new Button("dice");
    public static int cur_player = 0;
    public static int[] players_pos = { 0, 0, 0, 0 };
    public static Color[] player_color = { Color.DARKSLATEGRAY, Color.DARKCYAN, Color.DARKGREY, Color.DARKMAGENTA };
    public static void create_ladders(){
        Rectangle[] basic=new Rectangle[3];
        for(int i=0;i<3;i++){
            basic[i]=new Rectangle(70,200,Color.WHEAT);
            //basic[i].setStroke(Color.WHEAT);
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
            steps[i].getChildren().addAll(back[i], players_box[i],new Label(Integer.toString(i)));
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
}

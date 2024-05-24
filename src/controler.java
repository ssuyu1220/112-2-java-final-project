import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

class fill_handler implements EventHandler<MouseEvent> {
    private int idx;

    fill_handler(int idx) {
        this.idx = idx;
    }

    @Override
    public void handle(MouseEvent e) {
        controler.cur_pos = idx;
        controler.set_color();
        System.out.println(String.format("idx: %s, {%s %s %s %s}", idx, controler.graph[idx][0],
                controler.graph[idx][1], controler.graph[idx][2], controler.graph[idx][3]));
        if (controler.graph[idx][3] == -1) {
            controler.fill[idx].setFill(Color.LIGHTGRAY);
            controler.init_available_color();
            controler.check_color(idx);
            System.out.println(String.format("rgb: {%b %b %b}", controler.enable_rgb[0], controler.enable_rgb[1],
                    controler.enable_rgb[2]));
            controler.set_choose_color();
        }
    }
}

class choose_color_handler implements EventHandler<MouseEvent> {
    private int color;

    choose_color_handler(int color) {
        this.color = color;
    }

    @Override
    public void handle(MouseEvent e) {
        controler.set_color();
        if (controler.enable_rgb[color]) {
            if (color == 0) {
                controler.fill[controler.cur_pos].setFill(Color.PINK);
            } else if (color == 1) {
                controler.fill[controler.cur_pos].setFill(Color.LIGHTGREEN);
            } else if (color == 2) {
                controler.fill[controler.cur_pos].setFill(Color.LIGHTBLUE);
            }
        }
    }
}

class final_color_handler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
        System.out.println(String.format("cur_pos: %s", controler.cur_pos));
        controler.init_unavailable_color();
        if (controler.fill[controler.cur_pos].getFill() == Color.PINK) {
            controler.push_color(controler.cur_pos, 0);
        } else if (controler.fill[controler.cur_pos].getFill() == Color.LIGHTGREEN) {
            controler.push_color(controler.cur_pos, 1);
        } else if (controler.fill[controler.cur_pos].getFill() == Color.LIGHTBLUE)
            controler.push_color(controler.cur_pos, 2);
        else {
            controler.note.setText("You haven't choose a square to paint");
            return;
        }
        controler.bot_play();
    }
}

public class controler {
    public static int cur_pos, pre_color = -1;
    public static Label note = new Label("Choose a square to paint");
    public static Rectangle[] fill = new Rectangle[16];
    public static Rectangle red = new Rectangle(40, 40, Color.DARKRED);
    public static Rectangle green = new Rectangle(40, 40, Color.DARKGREEN);
    public static Rectangle blue = new Rectangle(40, 40, Color.DARKBLUE);
    public static boolean[] enable_rgb = { false, false, false };
    public static Button sure = new Button("OK");
    public static int[] dir = { 1, -1, 4, -4 };
    public static int[][] graph = {
            { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 },
            { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 },
            { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 },
            { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 }, { 0, 0, 0, -1 },
    };// {r,g,b}
    public static fill_handler[] square_fill = new fill_handler[16];
    public static choose_color_handler red_fill = new choose_color_handler(0);
    public static choose_color_handler green_fill = new choose_color_handler(1);
    public static choose_color_handler blue_fill = new choose_color_handler(2);
    public static final_color_handler color_sure = new final_color_handler();
    public static fill_handler[] ation_check = new fill_handler[16];
    public static ArrayList<Integer> available_square = new ArrayList<>();

    public static void push_color(int idx, int color) {
        available_square.remove(Integer.valueOf(idx));
        pre_color = color;
        graph[idx][3] = color;
        for (int i = 0; i < 4; i++) {
            if (idx + dir[i] > -1 && idx + dir[i] < 16) {
                if ((idx + dir[i]) / 4 == idx / 4 || (idx + dir[i]) % 4 == idx % 4) {
                    graph[idx + dir[i]][color] = 1;
                }
                // block the square(cannot assign any color)
                if (graph[idx + dir[i]][0] == 1 && graph[idx + dir[i]][1] == 1 && graph[idx + dir[i]][2] == 1) {
                    graph[idx + dir[i]][3] = 3;
                    fill[idx + dir[i]].setFill(Color.BLACK);
                    available_square.remove(Integer.valueOf(idx + dir[i]));
                }
            }
        }
    }

    public static void check_color(int idx) {
        if (graph[idx][0] == 1 || pre_color == 0)
            enable_rgb[0] = false;
        if (graph[idx][1] == 1 || pre_color == 1)
            enable_rgb[1] = false;
        if (graph[idx][2] == 1 || pre_color == 2)
            enable_rgb[2] = false;
    }

    public static void set_choose_color() {
        if (enable_rgb[0])
            red.setFill(Color.PINK);
        else
            red.setFill(Color.DARKRED);
        if (enable_rgb[1])
            green.setFill(Color.LIGHTGREEN);
        else
            green.setFill(Color.DARKGREEN);
        if (enable_rgb[2])
            blue.setFill(Color.LIGHTBLUE);
        else
            blue.setFill(Color.DARKBLUE);
        if (enable_rgb[0] || enable_rgb[1] || enable_rgb[2])
            note.setText("Choose a color to paint");
    }

    public static void init_available_color() {
        enable_rgb[0] = true;
        enable_rgb[1] = true;
        enable_rgb[2] = true;
    }

    public static void init_unavailable_color() {
        enable_rgb[0] = false;
        enable_rgb[1] = false;
        enable_rgb[2] = false;
        red.setFill(Color.DARKRED);
        green.setFill(Color.DARKGREEN);
        blue.setFill(Color.DARKBLUE);
    }

    public static void set_color() {
        for (int i = 0; i < 16; i++) {
            if (graph[i][3] == 0)
                fill[i].setFill(Color.PINK);
            else if (graph[i][3] == 1)
                fill[i].setFill(Color.LIGHTGREEN);
            else if (graph[i][3] == 2)
                fill[i].setFill(Color.LIGHTBLUE);
            else if (graph[i][3] == 3)
                fill[i].setFill(Color.BLACK);
            else
                fill[i].setFill(Color.WHITE);
            System.out.print(String.format("{%s%s%s%s} ", graph[i][0], graph[i][1], graph[i][2], graph[i][3]));
            if (i % 4 == 3)
                System.out.println();
        }
        System.out.println("--------------------------");
    }

    public static void build() {
        red.setOnMouseClicked(red_fill);
        green.setOnMouseClicked(green_fill);
        blue.setOnMouseClicked(blue_fill);
        sure.setOnAction(color_sure);
        for (int i = 0; i < 16; i++) {
            fill[i] = new Rectangle(50, 50);
            fill[i].setFill(Color.WHITE);
            fill[i].setStroke(Color.BLACK);
            fill[i].setStrokeWidth(2);
            square_fill[i] = new fill_handler(i);
            fill[i].setOnMouseClicked(square_fill[i]);
            available_square.add(i);
        }
    }

    public static void bot_play() {
        if (check_square(0))
            return;
        int idx = available_square.get((int) (Math.random() * available_square.size()));
        System.out.println(idx);
        ArrayList<Integer> color_list = new ArrayList<Integer>();
        if (graph[idx][0] == 0 && pre_color != 0)
            color_list.add(0);
        if (graph[idx][1] == 0 && pre_color != 1)
            color_list.add(1);
        if (graph[idx][2] == 0 && pre_color != 2)
            color_list.add(2);
        int color = color_list.get((int) (Math.random() * color_list.size()));
        available_square.remove(Integer.valueOf(idx));
        controler.push_color(idx, color);
        set_color();
        controler.note.setText("Choose a square to paint");
        if (check_square(1))
            return;
    }

    public static boolean check_square(int player) { // player 0:bot/1:you
        int cnt_unavailable = 0;
        for (int idx = 0; idx < 16; idx++) {
            boolean[] tmp_rgb = { false, false, false };
            if (graph[idx][0] == 1 || pre_color == 0)
                tmp_rgb[0] = true;
            if (graph[idx][1] == 1 || pre_color == 1)
                tmp_rgb[1] = true;
            if (graph[idx][2] == 1 || pre_color == 2)
                tmp_rgb[2] = true;
            if ((tmp_rgb[0] && tmp_rgb[1] && tmp_rgb[2]) || graph[idx][3] != -1) {
                cnt_unavailable++;
                fill[idx].setStroke(Color.BLACK);
                fill[idx].setStrokeWidth(2);
            } else {
                fill[idx].setStroke(Color.SILVER);
                fill[idx].setStrokeWidth(2);
            }
        }
        if (cnt_unavailable == 16) {
            if (player == 1) {
                note.setText("LOSE");
            } else {
                note.setText("WIN");
            }
            return true;
        } else {
            return false;
        }
    }

}

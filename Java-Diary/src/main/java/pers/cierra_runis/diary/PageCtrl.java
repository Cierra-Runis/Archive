package pers.cierra_runis.diary;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static pers.cierra_runis.diary.SystemInfo.*;

public class PageCtrl {
    public static VBox getLeftList(List<Diary> diaryList, Stage stage, boolean newToOld, Diary[] diaryInPage) {
        VBox LeftList = new VBox();
        LeftList.setLayoutX(0);
        LeftList.setLayoutY(450);
        LeftList.setSpacing(8);

        if (diaryList != null) {

            // 头部隔空用 VBox
            VBox blank = new VBox();
            blank.setMaxHeight(8);
            LeftList.getChildren().add(blank);

            System.out.printf("内部顺序 %s\n", newToOld);

            // 为每个日记都准备卡片
            for (Diary diary : diaryList) {
                HBox card = new HBox();
                card.setMaxWidth(0.18 * HOMEPAGE_WIDTH);
                card.setMinWidth(0.18 * HOMEPAGE_WIDTH);
                card.setPrefHeight(85);

                Text up = new Text();
                if (diary.titleString.length() > 8) {
                    up.setText(diary.titleString.substring(0, 8) + "…");
                } else {
                    up.setText(diary.titleString);
                }
                up.setSmooth(true);
                up.setFont(new Font(FONT_SC_BOLD.getName(), 20));
                up.setFill(PAINT_GRAY);
                up.setLayoutX(20);
                up.setLayoutY(28);

                Text middle = new Text(diary.date.substring(0, 4) + "-" + diary.date.substring(4, 6) + "-"
                        + diary.date.substring(6) + "  " + diary.time.substring(0, 2) + ":" + diary.time.substring(2, 4)
                        + ":" + diary.time.substring(4));
                middle.setSmooth(true);
                middle.setFont(FONT_SC_BOLD);
                middle.setFill(PAINT_GRAY);
                middle.setLayoutX(20);
                middle.setLayoutY(47);

                Text down = new Text();
                if (diary.textToStrings()[0].length() > 18) {
                    down.setText(diary.textToStrings()[0].substring(0, 18) + "…");
                } else {
                    down.setText(diary.textToStrings()[0] + "…");
                }
                down.setSmooth(true);
                down.setFont(FONT_SC_REGULAR);
                down.setFill(PAINT_GRAY);
                down.setLayoutX(20);
                down.setLayoutY(65);

                Pane pane = new Pane();
                pane.setPrefWidth(0.18 * HOMEPAGE_WIDTH);
                pane.setPrefHeight(80);
                pane.setBackground(BG_CARD);
                pane.getChildren().add(up);
                pane.getChildren().add(middle);
                pane.getChildren().add(down);

                card.getChildren().add(pane);
                card.setOnMouseClicked(event -> {
                    diaryInPage[0] = diary;
                });

                // 卡片加入左栏日记列表
                LeftList.getChildren().add(card);
            }
        }
        Collections.reverse(Objects.requireNonNull(diaryList));
        return LeftList;
    }

    public static VBox getUpMiddle(Diary diaryInPage) {

        VBox UpMiddle = new VBox();
        UpMiddle.setLayoutX(0);
        UpMiddle.setLayoutY(0);
        UpMiddle.setPrefWidth(0.54 * HOMEPAGE_WIDTH);
        UpMiddle.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        // 辅助用面板
        Pane pane = new Pane();
        String dateformat = diaryInPage.date.substring(0, 4) + "-" + diaryInPage.date.substring(4, 6) + "-"
                + diaryInPage.date.substring(6, 8);

        // 中栏的上部的年份、月份部分
        Text up = new Text(
                diaryInPage.date.substring(0, 4) + "年，" + Integer.valueOf(diaryInPage.date.substring(4, 6)) + "月");
        up.setFont(new Font(FONT_SC_REGULAR.getName(), 15));
        up.setFill(PAINT_GRAY);
        up.setSmooth(true);
        up.setLayoutX((0.54 * HOMEPAGE_WIDTH - up.getBoundsInLocal().getWidth()) / 2);
        up.setLayoutY(40);

        // 中栏的上部的日份部分
        Text middle = new Text(Integer.valueOf(diaryInPage.date.substring(6, 8)).toString());
        middle.setFont(new Font(FONT_SC_BOLD.getName(), 50));
        middle.setFill(PAINT_GRAY);
        middle.setSmooth(true);
        middle.setLayoutX((0.54 * HOMEPAGE_WIDTH - middle.getBoundsInLocal().getWidth()) / 2);
        middle.setLayoutY(90);

        // 中栏的上部的星期、时间部分
        Text down = new Text(Base.dateToWeek(dateformat) + "  " + diaryInPage.time.substring(0, 2) + ":"
                + diaryInPage.time.substring(2, 4));
        down.setFont(new Font(FONT_SC_REGULAR.getName(), 15));
        down.setFill(PAINT_GRAY);
        down.setSmooth(true);
        down.setLayoutX((0.54 * HOMEPAGE_WIDTH - down.getBoundsInLocal().getWidth()) / 2);
        down.setLayoutY(120);

        // 辅助用面板三孩了
        pane.getChildren().add(up);
        pane.getChildren().add(middle);
        pane.getChildren().add(down);
        pane.setPrefHeight(150);

        // 中栏的上部
        UpMiddle.getChildren().add(pane);

        return UpMiddle;
    }

    public static VBox getDownMiddle(Diary diaryInPage) {
        VBox DownMiddle = new VBox();
        DownMiddle.setLayoutX(50);
        DownMiddle.setLayoutY(10);
        DownMiddle.setPrefWidth(0.54 * HOMEPAGE_WIDTH);
        DownMiddle.setPrefHeight(HOMEPAGE_HEIGHT - 233);
        DownMiddle.setSpacing(8);

        // 为每一行都进行分析渲染
        for (String s : diaryInPage.textToStrings()) {

            // 准备每行文本的 HBox
            HBox hBox = new HBox();

            // 如果包含日期标签
            if (Base.isTimeLabel(s)) {
                // 则按日记标签渲染
                Pane time_part = new Pane();

                Label time_icon = new Label();
                time_icon.setPrefWidth(45);
                time_icon.setMinHeight(25);
                time_icon.setLayoutX(0);
                time_icon.setLayoutY(0);
                time_icon.setBackground(
                        new Background(new BackgroundImage(TIME, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(20, 20, false, false, false, false))));
                time_icon.setAlignment(Pos.CENTER_LEFT);

                Text text = new Text(s + "  ");
                text.setSmooth(true);
                text.setFont(new Font(FONT_SC_BOLD.getName(), 15));
                text.setFill(PAINT_LIGHTDARK);
                text.setLayoutX(45);
                text.setLayoutY(18);
                text.prefWidth(45);
                text.setWrappingWidth(text.getBoundsInLocal().getWidth());

                time_part.setBackground(BG_GRAY);
                time_part.getChildren().add(text);
                time_part.getChildren().add(time_icon);
                time_part.setMinHeight(25);
                HBox hBox1 = new HBox(time_part);
                hBox.getChildren().add(hBox1);

            } else {
                // 否则按普通文本渲染
                Text text = new Text(s);
                text.setSmooth(true);
                text.setFont(new Font(FONT_SC_BOLD.getName(), 15));
                text.setFill(PAINT_GRAY);
                text.prefWidth(0.54 * HOMEPAGE_WIDTH);
                text.setWrappingWidth(0.54 * HOMEPAGE_WIDTH);
                hBox.getChildren().add(text);
            }

            // 渲染完则加入中栏的下部
            DownMiddle.getChildren().add(hBox);
        }

        return DownMiddle;
    }

}

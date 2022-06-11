package pers.cierra_runis.diary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pers.cierra_runis.diary.api.HitokotoResponse;

import java.util.Objects;

import static pers.cierra_runis.diary.SystemInfo.*;

/**
 * 这个 Page 类用于显示主面板，是用户的最主要使用页面。</br>
 *
 * @author 8008121403
 * @author 8008121407
 * @version 1.0.0
 */
public class Page extends Application {

    static double x1; // 用于移动页面的参数
    static double y1; // 用于移动页面的参数
    static double x_stage; // 用于移动页面的参数
    static double y_stage; // 用于移动页面的参数

    Diary diaryInPage; // Page 类所操作的 Diary 类
    Diary[] diaries; // diarys 文件夹下的所有日记构成的 Diary 类数组

    /**
     * 有参构造 Page 类，显示传入日期所对应的日记。</br>
     *
     * @param date 构造 Page 类时传入的格式如 20220601 的日期字符串
     * @author 8008121403
     */
    public Page(String date) {

        diaryInPage = new Diary(date);
        diaries = Base.getAllDiary();
        diaryInPage.readDiary();

    }

    /**
     * 无参构造函数寻找最新一篇，没有日记则显示默认日记。</br>
     *
     * @author 8008121403
     */
    public Page() {

        diaryInPage = new Diary(DEFAULT_DATE);
        diaryInPage.textString = "你还没有写过日记。";

        System.out.println("正在寻找最新一篇日记");

        diaries = Base.getAllDiary();
        if (diaries != null) {
            diaryInPage = diaries[diaries.length - 1];
        }

    }

    /**
     * 创建窗口进行一个日记的显示。</br>
     *
     * @param stage 初始 stage 值
     * @author 8008121403
     * @author 8008121407
     */
    @Override
    public void start(Stage stage) {

        if (Objects.equals(diaryInPage.date, DEFAULT_DATE)) {
            System.out.println("你还没有写过日记。");
        }

        /////////////////////////////////////////////////////////////// 标题相关///////////////////////////////////////////////////////////////

        // 标题左部（暂无功能）
        Button setting = new Button("");
        setting.setPrefWidth(50);
        setting.setPrefHeight(30);
        setting.setAlignment(Pos.CENTER);
        setting.setBackground(
                new Background(new BackgroundImage(SETTING, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(20, 18, false, false, false, false))));
        HBox Title_Left = new HBox(setting);
        Title_Left.setPrefWidth(50);
        Title_Left.setPrefHeight(30);
        Title_Left.setLayoutX(0);
        Title_Left.setLayoutY(0);
        setting.setOnMouseEntered(mouseEvent -> Title_Left.setBackground(BG_DARK));
        setting.setOnMouseExited(mouseEvent -> Title_Left.setBackground(null));
        // TODO: 单击事件

        // 标题右部的最小化按钮
        Button minimize = new Button("");
        minimize.setPrefWidth(50);
        minimize.setPrefHeight(30);
        minimize.setBackground(
                new Background(new BackgroundImage(MINIMIZE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(20, 20, false, false, false, false))));
        HBox Title_Right_MINIMIZE = new HBox(minimize);
        Title_Right_MINIMIZE.setPrefWidth(50);
        Title_Right_MINIMIZE.setPrefHeight(30);
        Title_Right_MINIMIZE.setLayoutX(HOMEPAGE_WIDTH - 2 * 50);
        Title_Right_MINIMIZE.setLayoutY(0);
        minimize.setOnMouseEntered(mouseEvent -> Title_Right_MINIMIZE.setBackground(BG_DARK));
        minimize.setOnMouseExited(mouseEvent -> Title_Right_MINIMIZE.setBackground(null));
        minimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        // 标题右部的关闭按钮
        Button close = new Button("");
        close.setPrefWidth(50);
        close.setPrefHeight(30);
        close.setBackground(
                new Background(new BackgroundImage(CLOSE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(25, 25, false, false, false, false))));
        HBox Title_Right_CLOSE = new HBox(close);
        Title_Right_CLOSE.setPrefWidth(50);
        Title_Right_CLOSE.setPrefHeight(30);
        Title_Right_CLOSE.setLayoutX(HOMEPAGE_WIDTH - 50);
        Title_Right_CLOSE.setLayoutY(0);
        close.setOnMouseEntered(mouseEvent -> Title_Right_CLOSE.setBackground(BG_RED));
        close.setOnMouseExited(mouseEvent -> Title_Right_CLOSE.setBackground(null));
        close.setOnMousePressed(mouseEvent -> Title_Right_CLOSE.setBackground(BG_PINK));
        close.setOnMouseClicked(mouseEvent -> stage.close());

        // 标题底衬
        Label title = new Label(APP_NAME + " - " + diaryInPage.date + " - " + diaryInPage.titleString);
        title.setFont(FONT_SC_NORMAL);
        title.setTextFill(Color.LIGHTGRAY);
        HBox Title = new HBox(title);
        Title.setPrefWidth(HOMEPAGE_WIDTH);
        Title.setPrefHeight(30);
        Title.setBackground(BG_DARKER);
        Title.setAlignment(Pos.CENTER);
        Title.setOnMouseDragged(mouseEvent -> {
            stage.setX(x_stage + mouseEvent.getScreenX() - x1);
            stage.setY(y_stage + mouseEvent.getScreenY() - y1);
        });
        Title.setOnMousePressed(mouseEvent -> {
            x1 = mouseEvent.getScreenX();
            y1 = mouseEvent.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        });

        /////////////////////////////////////////////////////////////// 左栏相关///////////////////////////////////////////////////////////////

        // 左栏 VBox 框
        VBox Left = new VBox();
        Left.setLayoutX(0.01 * HOMEPAGE_WIDTH);
        Left.setLayoutY(30 + 41);
        Left.setPrefWidth(0.19 * HOMEPAGE_WIDTH);
        Left.setPrefHeight(HOMEPAGE_HEIGHT * HOMEPAGE_WIDTH);
        Left.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        // 左栏日记列表
        VBox LeftList = new VBox();
        LeftList.setLayoutX(0);
        LeftList.setLayoutY(450);
        LeftList.setSpacing(8);

        if (diaries != null) {

            // 头部隔空用 VBox
            VBox blank = new VBox();
            blank.setMaxHeight(8);
            LeftList.getChildren().add(blank);

            // 准备名为 list 的 Diary 类数组，按 newToOld 排序
            Diary[] list = new Diary[diaries.length];
            if (!newToOld) {
                list = diaries;
            } else {
                for (int i = 0; i < diaries.length; i++) {
                    list[i] = diaries[diaries.length - i - 1];
                }
            }
            // 为每个日记都准备卡片
            for (Diary diary : list) {
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
                    stage.close();
                    new Page(diary.date).start(new Stage());
                });

                // 卡片加入左栏日记列表
                LeftList.getChildren().add(card);
            }
        }

        // 左栏 VBox 栏获得左栏日记列表
        Left.getChildren().add(LeftList);

        // 左部 VBox 拖动的具体实现
        Left.setOnMouseDragged(mouseEvent -> {

            if ((HOMEPAGE_HEIGHT - LeftList.getHeight() - 50) >= 30 + 41) {
                System.out.println("不滑动");
            } else {
                Left.setLayoutY(y_stage + mouseEvent.getScreenY() - y1);
                if (Left.getLayoutY() < (HOMEPAGE_HEIGHT - LeftList.getHeight() - 50)) {
                    Left.setLayoutY(HOMEPAGE_HEIGHT - LeftList.getHeight() - 50);
                }
                if (Left.getLayoutY() > 30 + 41) {
                    Left.setLayoutY(30 + 41);
                }
            }

        });

        // 左部 VBox 滚动的具体实现
        Left.setOnScroll(scrollEvent -> {

            y1 = -scrollEvent.getDeltaY();
            y_stage = Left.getLayoutY();

            if ((HOMEPAGE_HEIGHT - LeftList.getHeight() - 50) >= 30 + 41) {
                System.out.println("不滑动");
            } else {
                Left.setLayoutY(y_stage + scrollEvent.getDeltaY() - y1);
                if (Left.getLayoutY() < (HOMEPAGE_HEIGHT - LeftList.getHeight() - 50)) {
                    Left.setLayoutY(HOMEPAGE_HEIGHT - 8 - LeftList.getHeight() - 50);
                }
                if (Left.getLayoutY() > 30 + 41) {
                    Left.setLayoutY(30 + 41);
                }
            }
        });
        Left.setOnMousePressed(mouseEvent -> {
            // 按下获取初始值
            y1 = mouseEvent.getScreenY();
            y_stage = Left.getLayoutY();
        });

        // 左部工具栏的添加按钮
        Button add = new Button("");
        add.setPrefWidth(30);
        add.setPrefHeight(30);
        add.setAlignment(Pos.CENTER);
        add.setBackground(new Background(
                new BackgroundImage(ADD_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
        add.setLayoutX(12);
        add.setLayoutY(35);
        add.setOnMousePressed(mouseEvent -> add.setBackground(
                new Background(new BackgroundImage(ADD_PRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        add.setOnMouseExited(mouseEvent -> add.setBackground(new Background(
                new BackgroundImage(ADD_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        add.setOnMouseClicked(mouseEvent -> {
            stage.setOpacity(0.6);
            String dateFromDateWindow = DateWindow.display();
            stage.close();
            if (dateFromDateWindow != null && !dateFromDateWindow.equals("")) {
                if (new Editor(dateFromDateWindow).display()) {
                    new Page(dateFromDateWindow).start(new Stage());
                } else {
                    new Page().start(new Stage());
                }
            } else {
                new Page().start(new Stage());
            }
        });

        // 左部工具栏的排序按钮
        Button sort = new Button();
        sort.setPrefWidth(30);
        sort.setPrefHeight(30);
        sort.setAlignment(Pos.CENTER);
        sort.setBackground(new Background(new BackgroundImage(newToOld ? SORTDOWN_UNPRESSED : SORTUP_UNPRESSED,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(30, 30, false, false, false, false))));
        sort.setLayoutX(240);
        sort.setLayoutY(35);
        sort.setOnMousePressed(mouseEvent -> sort
                .setBackground(new Background(new BackgroundImage(newToOld ? SORTDOWN_PRESSED : SORTUP_PRESSED,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(30, 30, false, false, false, false)))));
        sort.setOnMouseExited(mouseEvent -> sort
                .setBackground(new Background(new BackgroundImage(newToOld ? SORTDOWN_UNPRESSED : SORTUP_UNPRESSED,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(30, 30, false, false, false, false)))));
        sort.setOnMouseClicked(mouseEvent -> {
            newToOld = !newToOld;
            stage.close();
            new Page(diaryInPage.date).start(new Stage());
        });

        // 左部工具栏的底衬
        Label left_tool = new Label("");
        HBox Left_Tool = new HBox(left_tool);
        Left_Tool.setPrefWidth(0.2 * HOMEPAGE_WIDTH);
        Left_Tool.setPrefHeight(41);
        Left_Tool.setLayoutX(0);
        Left_Tool.setLayoutY(30);
        Left_Tool.setBackground(BG_DARK);
        Left_Tool.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_LIGHTDARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        Left_Tool.setAlignment(Pos.CENTER);

        /////////////////////////////////////////////////////////////// 中栏相关///////////////////////////////////////////////////////////////

        // 中栏 VBox 框
        VBox Middle = new VBox();
        Middle.setLayoutX(0.23 * HOMEPAGE_WIDTH);
        Middle.setLayoutY(30);
        Middle.setPrefWidth(0.54 * HOMEPAGE_WIDTH);
        Middle.setPrefHeight(HOMEPAGE_WIDTH * HOMEPAGE_HEIGHT);
        Middle.setSpacing(8);

        // 中栏的上部（用于显示时间信息）///////////////////////////////////
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

        // 中栏的下部（用于显示日记的渲染效果）///////////////////////////////////
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

        // 中栏（用于整合上下部）///////////////////////////////////
        Middle.getChildren().add(UpMiddle);
        Middle.getChildren().add(DownMiddle);

        // 中栏 VBox 拖动的具体实现
        Middle.setOnMouseDragged(mouseEvent -> {

            if ((HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50) >= 30) {
                System.out.println("不滑动");
            } else {
                Middle.setLayoutY(y_stage + mouseEvent.getScreenY() - y1);
                if (Middle.getLayoutY() < (HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50)) {
                    Middle.setLayoutY(HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50);
                }
                if (Middle.getLayoutY() > 30) {
                    Middle.setLayoutY(30);
                }
            }

        });

        // 中栏 VBox 滚动的具体实现
        Middle.setOnScroll(scrollEvent -> {

            y1 = -scrollEvent.getDeltaY();
            y_stage = Middle.getLayoutY();

            if ((HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50) >= 30) {
                System.out.println("不滑动");
            } else {
                Middle.setLayoutY(y_stage + scrollEvent.getDeltaY() - y1);
                if (Middle.getLayoutY() < (HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50)) {
                    Middle.setLayoutY(HOMEPAGE_HEIGHT - UpMiddle.getHeight() - DownMiddle.getHeight() - 50);
                }
                if (Middle.getLayoutY() > 30) {
                    Middle.setLayoutY(30);
                }
            }
        });
        Middle.setOnMousePressed(mouseEvent -> {
            // 按下获取初始值
            y1 = mouseEvent.getScreenY();
            y_stage = Middle.getLayoutY();
        });

        /////////////////////////////////////////////////////////////// 底栏相关///////////////////////////////////////////////////////////////

        // 底部工具栏的删除按钮
        Button delete = new Button("");
        delete.setPrefWidth(30);
        delete.setPrefHeight(30);
        delete.setAlignment(Pos.CENTER);
        delete.setBackground(new Background(
                new BackgroundImage(DELETE_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
        delete.setLayoutX(0.2 * HOMEPAGE_WIDTH + 35);
        delete.setLayoutY(HOMEPAGE_HEIGHT - 35);
        delete.setOnMousePressed(mouseEvent -> {
            if (!Objects.equals(diaryInPage.date, DEFAULT_DATE)) {
                delete.setBackground(new Background(
                        new BackgroundImage(DELETE_PRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
            }
        });
        delete.setOnMouseReleased(mouseEvent -> delete.setBackground(new Background(
                new BackgroundImage(DELETE_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        delete.setOnMouseExited(mouseEvent -> delete.setBackground(new Background(
                new BackgroundImage(DELETE_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        delete.setOnMouseClicked(mouseEvent -> {
            if (!Objects.equals(diaryInPage.date, DEFAULT_DATE)) {
                stage.setOpacity(0.6);
                if (AlertWindow.display("删除确认", "是否删除")) {
                    diaryInPage.deleteDiary();
                    stage.close();
                    new Page().start(new Stage());
                }
                stage.setOpacity(0.9);
            }
        });

        // 底部工具栏的编辑按钮
        Button edit = new Button("");
        edit.setPrefWidth(30);
        edit.setPrefHeight(30);
        edit.setAlignment(Pos.CENTER);
        edit.setBackground(new Background(
                new BackgroundImage(EDIT_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
        edit.setLayoutX(0.8 * HOMEPAGE_WIDTH - 65);
        edit.setLayoutY(HOMEPAGE_HEIGHT - 35);
        edit.setOnMousePressed(mouseEvent -> {
            if (!Objects.equals(diaryInPage.date, DEFAULT_DATE)) {
                edit.setBackground(new Background(
                        new BackgroundImage(EDIT_PRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
            }
        });
        edit.setOnMouseReleased(mouseEvent -> edit.setBackground(new Background(
                new BackgroundImage(EDIT_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        edit.setOnMouseExited(mouseEvent -> edit.setBackground(new Background(
                new BackgroundImage(EDIT_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
        edit.setOnMouseClicked(mouseEvent -> {
            if (!Objects.equals(diaryInPage.date, DEFAULT_DATE)) {
                stage.setOpacity(0);
                System.out.printf("你想编辑 %s 的日记\n", diaryInPage.date);
                new Editor(diaryInPage.date).display();
                new Page().start(new Stage());
                stage.close();
            }
        });

        // 底部工具栏的底衬
        Label tool = new Label("");
        HBox Tool = new HBox(tool);
        Tool.setPrefWidth(0.6 * HOMEPAGE_WIDTH);
        Tool.setPrefHeight(41);
        Tool.setLayoutX(0.2 * HOMEPAGE_WIDTH);
        Tool.setLayoutY(HOMEPAGE_HEIGHT - 40);
        Tool.setBackground(BG_DARK);
        Tool.setBorder(new Border(new BorderStroke(PAINT_LIGHTDARK, PAINT_DARK, PAINT_DARK, PAINT_DARK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        Tool.setAlignment(Pos.CENTER);

        /////////////////////////////////////////////////////////////// 右栏相关///////////////////////////////////////////////////////////////

        // 右栏 VBox 框
        VBox Right = new VBox();
        Right.setSpacing(8);
        Right.setPrefWidth(0.2 * HOMEPAGE_WIDTH);
        Right.setPrefHeight(HOMEPAGE_WIDTH * HOMEPAGE_HEIGHT);
        Right.setLayoutX(0.8 * HOMEPAGE_WIDTH);
        Right.setLayoutY(30);
        Right.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        // 头像实现借鉴于 https://www.jianshu.com/p/779090da020f
        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(70);
        stackPane.setPrefHeight(70);
        stackPane.setLayoutX(0.8 * HOMEPAGE_WIDTH + 15);
        stackPane.setLayoutY(55);
        stackPane.setPadding(new Insets(5));
        stackPane.setEffect(new DropShadow(5, 2.0, 2.0, Color.rgb(33, 37, 43)));
        ImageView imageView = new ImageView(PROFILE_PHOTO);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setClip(new Circle(35, 35, 35));
        imageView.setSmooth(true);
        stackPane.getChildren().add(imageView);

        // 用户名
        Label user_name = new Label(USER_NAME);
        user_name.setTextFill(PAINT_GRAY);
        user_name.setFont(new Font(FONT_SC_BOLD.getName(), 21));
        user_name.setLayoutX(0.8 * HOMEPAGE_WIDTH + 105);
        user_name.setLayoutY(65);

        // 用户格言
        Text motto = new Text(MOTTO);
        motto.setLayoutX(0.8 * HOMEPAGE_WIDTH + 105);
        motto.setLayoutY(115);
        motto.setFont(FONT_SC_BOLD);
        motto.setFill(PAINT_GRAY);
        motto.setWrappingWidth(160);

        // 右栏菜单
        VBox Menu = new VBox();
        Menu.setLayoutX(0.8 * HOMEPAGE_WIDTH);
        Menu.setLayoutY(160);
        Menu.setPrefWidth(0.2 * HOMEPAGE_WIDTH);

        // 右栏菜单的 关于 选项
        VBox About = new VBox();
        About.setPrefHeight(45);
        Pane AboutPane = new Pane();
        About.setBorder(new Border(new BorderStroke(PAINT_LIGHTDARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        Button about_icon = new Button();
        about_icon.setMinWidth(45);
        about_icon.setMinHeight(45);
        about_icon.setLayoutX(8);
        about_icon.setBackground(
                new Background(new BackgroundImage(ABOUT, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
        Text about_text = new Text("关于");
        about_text.setLayoutX(60);
        about_text.setLayoutY(28);
        about_text.setFill(PAINT_GRAY);
        about_text.setFont(new Font(FONT_SC_BOLD.getName(), 16));
        AboutPane.getChildren().add(about_icon);
        AboutPane.getChildren().add(about_text);
        About.getChildren().add(AboutPane);
        About.setOnMouseClicked(event -> {
            stage.setOpacity(0.6);
            AboutWindow.display();
            stage.setOpacity(0.9);
        });

        // 关于 选项成了菜单的小孩
        Menu.getChildren().add(About);

        // 右栏 Hitokoto 小组件
        VBox Hitokoto = new VBox();
        Hitokoto.setLayoutX(0.81 * HOMEPAGE_WIDTH);
        Hitokoto.setLayoutY(0.95 * HOMEPAGE_HEIGHT);
        Hitokoto.setMaxWidth(0.18 * HOMEPAGE_WIDTH);
        Hitokoto.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        Text sentence = new Text();
        // 线程相关由 谢佬（https://github.com/WOo0W） 提供帮助
        Thread httpThread = new Thread(() -> {
            try {
                HitokotoResponse hitokotoResponse = HitokotoResponse.getHitokoto();
                String hitokoto = hitokotoResponse.hitokoto;
                String yurai = hitokotoResponse.from;
                String uuid = hitokotoResponse.uuid;
                Platform.runLater(() -> {
                    sentence.setText("『 " + hitokoto + " 』" + "  ——  " + yurai);
                    Hitokoto.setOnMouseClicked(
                            event -> getHostServices().showDocument("https://hitokoto.cn/?uuid=" + uuid));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        httpThread.start();

        sentence.setFill(PAINT_GRAY);
        sentence.setFont(FONT_SC_BOLD);
        sentence.setSmooth(true);
        sentence.setWrappingWidth(0.18 * HOMEPAGE_WIDTH);

        Hitokoto.getChildren().add(sentence);

        /////////////////////////////////////////////////////////////// 其他相关///////////////////////////////////////////////////////////////

        // 底衬
        HBox Body = new HBox();
        Body.setPrefWidth(HOMEPAGE_WIDTH);
        Body.setPrefHeight(HOMEPAGE_HEIGHT);
        Body.setBackground(BG_DARK);

        // 加组
        Group group = new Group();
        group.getChildren().add(Body);
        group.getChildren().add(Left);
        group.getChildren().add(Middle);
        group.getChildren().add(Right);
        group.getChildren().add(Title);
        group.getChildren().add(Title_Left);
        group.getChildren().add(Title_Right_MINIMIZE);
        group.getChildren().add(Title_Right_CLOSE);
        group.getChildren().add(Tool);
        group.getChildren().add(Left_Tool);
        group.getChildren().add(delete);
        group.getChildren().add(edit);
        group.getChildren().add(add);
        group.getChildren().add(sort);
        group.getChildren().add(Hitokoto);
        group.getChildren().add(stackPane);
        group.getChildren().add(user_name);
        group.getChildren().add(motto);
        group.getChildren().add(Menu);

        Scene scene = new Scene(group);
        scene.setFill(null);

        // 因为要使用 scene 而放到后面的 鼠标指针图标变换的具体实现
        Hitokoto.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
        Hitokoto.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        // 定位
        stage.setX((SCREEN_WIDTH - HOMEPAGE_WIDTH) / 2);
        stage.setY((SCREEN_HEIGHT - HOMEPAGE_HEIGHT) / 2);

        // 配置
        stage.setTitle(APP_NAME);
        stage.getIcons().add(ICON);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(HOMEPAGE_WIDTH);
        stage.setHeight(HOMEPAGE_HEIGHT);
        stage.setOpacity(0.9);

        // 布局
        stage.setScene(scene);

        // 显示
        stage.show();

    }

}

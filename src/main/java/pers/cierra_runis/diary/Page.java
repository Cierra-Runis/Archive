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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pers.cierra_runis.diary.api.HitokotoResponse;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

    Diary[] diaryInPage = new Diary[1]; // Page 类所操作的 Diary 类
    List<Diary> diaryList; // database/diarys.json 文件解析构成的 Diary 类列表

    /**
     * 有参构造 Page 类，显示传入日期所对应的日记。</br>
     *
     * @param date 构造 Page 类时传入的格式如 20220601 的日期字符串
     * @author 8008121403
     */
    public Page(String date) {

        diaryList = Base.getDiaryList();
        diaryInPage[0] = Objects.requireNonNull(diaryList).get(Diary.backIndex(date));

    }

    /**
     * 无参构造函数寻找最新一篇，没有日记则显示默认日记。</br>
     *
     * @author 8008121403
     */
    public Page() {

        diaryInPage[0] = new Diary(DEFAULT_DATE);
        diaryInPage[0].textString = "你还没有写过日记。";
        diaryList = Base.getDiaryList();
        if (diaryList != null) {
            diaryInPage[0] = diaryList.get(0);
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (Objects.equals(diaryInPage[0].date, DEFAULT_DATE)) {
                    System.out.println("你还没有写过日记。");
                }

                /////////////////////////////////////////////////////////////// 提前准备///////////////////////////////////////////////////////////////

                // 底衬
                HBox Body = new HBox();

                // 标题底衬
                Label title = new Label("");
                HBox Title = new HBox();

                // 标题左部（暂无功能）
                Button setting = new Button("");
                HBox Title_Left = new HBox();

                // 标题右部的最小化按钮
                Button minimize = new Button("");
                HBox Title_Right_MINIMIZE = new HBox();

                // 标题右部的关闭按钮
                Button close = new Button("");
                HBox Title_Right_CLOSE = new HBox();

                // 左栏 VBox 框
                VBox Left = new VBox();

                // 左栏日记列表
                VBox[] LeftList = {PageCtrl.getLeftList(diaryList, stage, newToOld, diaryInPage)};

                // 左部工具栏的添加按钮
                Button add = new Button("");

                // 左部工具栏的下载按钮
                Button download = new Button();

                // 左部工具栏的排序按钮
                Button sort = new Button();

                // 左部工具栏的底衬
                Label left_tool = new Label("");
                HBox Left_Tool = new HBox();

                // 中栏 VBox 框
                VBox Middle = new VBox();

                // 中栏的上部（用于显示时间信息）
                VBox UpMiddle = PageCtrl.getUpMiddle(diaryInPage[0]);

                // 中栏的下部（用于显示日记的渲染效果）
                VBox DownMiddle = PageCtrl.getDownMiddle(diaryInPage[0]);

                // 底部工具栏的删除按钮
                Button delete = new Button("");

                // 底部工具栏的编辑按钮
                Button edit = new Button("");

                // 底部工具栏的底衬
                Label tool = new Label("");
                HBox Tool = new HBox();

                // 右栏 VBox 框
                VBox Right = new VBox();

                // 头像实现借鉴于 https://www.jianshu.com/p/779090da020f
                StackPane stackPane = new StackPane();
                ImageView imageView = new ImageView(PROFILE_PHOTO);

                // 用户名
                Label user_name = new Label(USER_NAME);

                // 用户格言
                Text motto = new Text(MOTTO);

                // 右栏菜单
                VBox Menu = new VBox();

                // 右栏菜单的 关于 选项
                VBox About = new VBox();
                Pane AboutPane = new Pane();
                Button about_icon = new Button();
                Text about_text = new Text("关于");

                // 右栏 Hitokoto 小组件
                VBox Hitokoto = new VBox();
                Text sentence = new Text();

                /////////////////////////////////////////////////////////////// 标题相关///////////////////////////////////////////////////////////////

                // 标题左部（暂无功能）
                setting.setPrefWidth(50);
                setting.setPrefHeight(30);
                setting.setAlignment(Pos.CENTER);
                setting.setBackground(
                        new Background(new BackgroundImage(SETTING, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(20, 18, false, false, false, false))));

                Title_Left.getChildren().add(setting);
                Title_Left.setPrefWidth(50);
                Title_Left.setPrefHeight(30);
                Title_Left.setLayoutX(0);
                Title_Left.setLayoutY(0);
                setting.setOnMouseEntered(mouseEvent -> Title_Left.setBackground(BG_DARK));
                setting.setOnMouseExited(mouseEvent -> Title_Left.setBackground(null));
                // TODO: 单击事件

                // 标题右部的最小化按钮
                minimize.setPrefWidth(50);
                minimize.setPrefHeight(30);
                minimize.setBackground(
                        new Background(new BackgroundImage(MINIMIZE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(20, 20, false, false, false, false))));

                Title_Right_MINIMIZE.getChildren().add(minimize);
                Title_Right_MINIMIZE.setPrefWidth(50);
                Title_Right_MINIMIZE.setPrefHeight(30);
                Title_Right_MINIMIZE.setLayoutX(HOMEPAGE_WIDTH - 2 * 50);
                Title_Right_MINIMIZE.setLayoutY(0);
                minimize.setOnMouseEntered(mouseEvent -> Title_Right_MINIMIZE.setBackground(BG_DARK));
                minimize.setOnMouseExited(mouseEvent -> Title_Right_MINIMIZE.setBackground(null));
                minimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

                // 标题右部的关闭按钮
                close.setPrefWidth(50);
                close.setPrefHeight(30);
                close.setBackground(
                        new Background(new BackgroundImage(CLOSE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(25, 25, false, false, false, false))));
                Title_Right_CLOSE.getChildren().add(close);
                Title_Right_CLOSE.setPrefWidth(50);
                Title_Right_CLOSE.setPrefHeight(30);
                Title_Right_CLOSE.setLayoutX(HOMEPAGE_WIDTH - 50);
                Title_Right_CLOSE.setLayoutY(0);
                close.setOnMouseEntered(mouseEvent -> Title_Right_CLOSE.setBackground(BG_RED));
                close.setOnMouseExited(mouseEvent -> Title_Right_CLOSE.setBackground(null));
                close.setOnMousePressed(mouseEvent -> Title_Right_CLOSE.setBackground(BG_PINK));
                close.setOnMouseClicked(mouseEvent -> stage.close());

                // 标题底衬
                title.setText(APP_NAME + " - " + diaryInPage[0].date + " - " + diaryInPage[0].titleString);
                title.setFont(FONT_SC_NORMAL);
                title.setTextFill(Color.LIGHTGRAY);
                Title.getChildren().add(title);
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
                Left.setLayoutX(0.01 * HOMEPAGE_WIDTH);
                Left.setLayoutY(30 + 41);
                Left.setPrefWidth(0.19 * HOMEPAGE_WIDTH);
                Left.setPrefHeight(HOMEPAGE_HEIGHT * HOMEPAGE_WIDTH);
                Left.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK, PAINT_DARK,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

                // 左栏 VBox 栏获得左栏日记列表
                Left.getChildren().add(LeftList[0]);

                // 左部 VBox 拖动的具体实现
                Left.setOnMouseDragged(mouseEvent -> {

                    if ((HOMEPAGE_HEIGHT - LeftList[0].getHeight() - 50) >= 30 + 41) {
                        System.out.println("不滑动");
                    } else {
                        Left.setLayoutY(y_stage + mouseEvent.getScreenY() - y1);
                        if (Left.getLayoutY() < (HOMEPAGE_HEIGHT - LeftList[0].getHeight() - 50)) {
                            Left.setLayoutY(HOMEPAGE_HEIGHT - LeftList[0].getHeight() - 50);
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

                    if ((HOMEPAGE_HEIGHT - LeftList[0].getHeight() - 50) >= 30 + 41) {
                        System.out.println("不滑动");
                    } else {
                        Left.setLayoutY(y_stage + scrollEvent.getDeltaY() - y1);
                        if (Left.getLayoutY() < (HOMEPAGE_HEIGHT - LeftList[0].getHeight() - 50)) {
                            Left.setLayoutY(HOMEPAGE_HEIGHT - 8 - LeftList[0].getHeight() - 50);
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
                    }
                });

                // 左部工具栏的下载按钮
                download.setPrefWidth(30);
                download.setPrefHeight(30);
                download.setAlignment(Pos.CENTER);
                download.setBackground(new Background(
                        new BackgroundImage(DOWNLOAD_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
                download.setLayoutX(240);
                download.setLayoutY(35);
                download.setOnMousePressed(mouseEvent -> download.setBackground(
                        new Background(new BackgroundImage(DOWNLOAD_PRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
                download.setOnMouseExited(mouseEvent -> download.setBackground(new Background(
                        new BackgroundImage(DOWNLOAD_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false)))));
                download.setOnMouseClicked(mouseEvent -> {
                    stage.setOpacity(0.6);
                    if (AlertWindow.display("跳转确认", "需工具将本地数据覆盖，是否继续", true, true)) {
                        try {
                            Desktop.getDesktop().open(new File("resources/DiaryExportTool"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("选择 exportDiary.json 文件");
                        fileChooser.setInitialDirectory(new File("resources/DiaryExportTool/DiaryExportTool"));
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("导出文件", "*.json"));
                        File selectedFile = fileChooser.showOpenDialog(new Stage());
                        while (selectedFile == null) {
                            AlertWindow.display("提示", "请选取导出的 json 文件", false, true);
                            selectedFile = fileChooser.showOpenDialog(new Stage());
                        }
                        Diary.jsonToFiles(selectedFile);
                    }
                    stage.close();
                    new Page().start(new Stage());
                });

                // 左部工具栏的排序按钮
                sort.setPrefWidth(30);
                sort.setPrefHeight(30);
                sort.setAlignment(Pos.CENTER);
                sort.setBackground(new Background(new BackgroundImage(newToOld ? SORTDOWN_UNPRESSED : SORTUP_UNPRESSED,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(30, 30, false, false, false, false))));
                sort.setLayoutX(280);
                sort.setLayoutY(35);
                sort.setOnMousePressed(mouseEvent -> sort
                        .setBackground(new Background(new BackgroundImage(newToOld ? SORTUP_PRESSED : SORTDOWN_PRESSED,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(30, 30, false, false, false, false)))));
                sort.setOnMouseReleased(mouseEvent -> sort
                        .setBackground(new Background(new BackgroundImage(newToOld ? SORTUP_UNPRESSED : SORTDOWN_UNPRESSED,
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(30, 30, false, false, false, false)))));
                sort.setOnMouseClicked(mouseEvent -> {
                    XQW
                            newToOld = !newToOld;
                    Left.getChildren().remove(LeftList[0]);
                    LeftList[0] = PageCtrl.getLeftList(diaryList, stage, newToOld, diaryInPage);
                    Left.getChildren().add(LeftList[0]);

                });

                // 左部工具栏的底衬
                Left_Tool.getChildren().add(left_tool);
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
                Middle.setLayoutX(0.23 * HOMEPAGE_WIDTH);
                Middle.setLayoutY(30);
                Middle.setPrefWidth(0.54 * HOMEPAGE_WIDTH);
                Middle.setPrefHeight(HOMEPAGE_WIDTH * HOMEPAGE_HEIGHT);
                Middle.setSpacing(8);

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
                delete.setPrefWidth(30);
                delete.setPrefHeight(30);
                delete.setAlignment(Pos.CENTER);
                delete.setBackground(new Background(
                        new BackgroundImage(DELETE_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
                delete.setLayoutX(0.2 * HOMEPAGE_WIDTH + 35);
                delete.setLayoutY(HOMEPAGE_HEIGHT - 35);
                delete.setOnMousePressed(mouseEvent -> {
                    if (!Objects.equals(diaryInPage[0].date, DEFAULT_DATE)) {
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
                    if (!Objects.equals(diaryInPage[0].date, DEFAULT_DATE)) {
                        stage.setOpacity(0.6);
                        if (AlertWindow.display("删除确认", "是否删除", false, true)) {
                            diaryInPage[0].deleteDiary();
                            stage.close();
                            new Page().start(new Stage());
                        }
                        stage.setOpacity(0.9);
                    }
                });

                // 底部工具栏的编辑按钮
                edit.setPrefWidth(30);
                edit.setPrefHeight(30);
                edit.setAlignment(Pos.CENTER);
                edit.setBackground(new Background(
                        new BackgroundImage(EDIT_UNPRESSED, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
                edit.setLayoutX(0.8 * HOMEPAGE_WIDTH - 65);
                edit.setLayoutY(HOMEPAGE_HEIGHT - 35);
                edit.setOnMousePressed(mouseEvent -> {
                    if (!Objects.equals(diaryInPage[0].date, DEFAULT_DATE)) {
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
                    if (!Objects.equals(diaryInPage[0].date, DEFAULT_DATE)) {
                        stage.setOpacity(0);
                        System.out.printf("你想编辑 %s 的日记\n", diaryInPage[0].date);
                        new Editor(diaryInPage[0].date).display();
                        new Page().start(new Stage());
                        stage.close();
                    }
                });

                // 底部工具栏的底衬
                Tool.getChildren().add(tool);
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
                Right.setSpacing(8);
                Right.setPrefWidth(0.2 * HOMEPAGE_WIDTH);
                Right.setPrefHeight(HOMEPAGE_WIDTH * HOMEPAGE_HEIGHT);
                Right.setLayoutX(0.8 * HOMEPAGE_WIDTH);
                Right.setLayoutY(30);
                Right.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

                // 头像实现借鉴于 https://www.jianshu.com/p/779090da020f
                stackPane.setPrefWidth(70);
                stackPane.setPrefHeight(70);
                stackPane.setLayoutX(0.8 * HOMEPAGE_WIDTH + 15);
                stackPane.setLayoutY(55);
                stackPane.setPadding(new Insets(5));
                stackPane.setEffect(new DropShadow(5, 2.0, 2.0, Color.rgb(33, 37, 43)));
                imageView.setFitWidth(70);
                imageView.setFitHeight(70);
                imageView.setClip(new Circle(35, 35, 35));
                imageView.setSmooth(true);
                stackPane.getChildren().add(imageView);

                // 用户名
                user_name.setTextFill(PAINT_GRAY);
                user_name.setFont(new Font(FONT_SC_BOLD.getName(), 21));
                user_name.setLayoutX(0.8 * HOMEPAGE_WIDTH + 105);
                user_name.setLayoutY(65);

                // 用户格言
                motto.setLayoutX(0.8 * HOMEPAGE_WIDTH + 105);
                motto.setLayoutY(115);
                motto.setFont(FONT_SC_BOLD);
                motto.setFill(PAINT_GRAY);
                motto.setWrappingWidth(160);

                // 右栏菜单
                Menu.setLayoutX(0.8 * HOMEPAGE_WIDTH);
                Menu.setLayoutY(160);
                Menu.setPrefWidth(0.2 * HOMEPAGE_WIDTH);

                // 右栏菜单的 关于 选项
                About.setPrefHeight(45);
                About.setBorder(new Border(new BorderStroke(PAINT_LIGHTDARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                        BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
                about_icon.setMinWidth(45);
                about_icon.setMinHeight(45);
                about_icon.setLayoutX(8);
                about_icon.setBackground(
                        new Background(new BackgroundImage(ABOUT, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
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
                Hitokoto.setLayoutX(0.81 * HOMEPAGE_WIDTH);
                Hitokoto.setLayoutY(0.95 * HOMEPAGE_HEIGHT);
                Hitokoto.setMaxWidth(0.18 * HOMEPAGE_WIDTH);
                Hitokoto.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                        BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

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
                if (connectToNideriji) {
                    group.getChildren().add(download);
                }
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
        });

    }

}

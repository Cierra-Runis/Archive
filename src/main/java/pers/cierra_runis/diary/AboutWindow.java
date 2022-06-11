package pers.cierra_runis.diary;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static pers.cierra_runis.diary.SystemInfo.*;

/**
 * 这个 AboutWindow 类创建窗口显示相关信息。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class AboutWindow {

    /** 用于移动页面的参数 */
    static double x1;
    /** 用于移动页面的参数 */
    static double y1;
    /** 用于移动页面的参数 */
    static double x_stage;
    /** 用于移动页面的参数 */
    static double y_stage;

    /**
     * 创建窗口显示相关信息。<br/>
     *
     * @author 8008121403
     */
    public static void display() {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // 大 Icon
        ImageView Big_Icon = new ImageView(ICON);
        Big_Icon.setFitHeight(60);
        Big_Icon.setFitWidth(60);
        Big_Icon.setLayoutX(20);
        Big_Icon.setLayoutY(45);

        // 显示 APP 名称和对外版本号
        Text Name = new Text(APP_NAME + " ver." + APP_PUBLIC_VERSION);
        Name.setLayoutX(105);
        Name.setLayoutY(70);
        Name.setFont(new Font(FONT_SC_BOLD.getName(), 18));
        Name.setFill(PAINT_GRAY);
        Name.setSmooth(true);

        // 详细的对外版本号和构建信息
        Text PubicVersion = new Text("对外版本号 " + APP_PUBLIC_VERSION + "，于 " + APP_DATE + " 构建");
        PubicVersion.setLayoutX(105);
        PubicVersion.setLayoutY(90);
        PubicVersion.setFill(PAINT_GRAY);
        PubicVersion.setSmooth(false);

        Text Builders = new Text("参与人员\n8008121403\t8008121407");
        Builders.setLayoutX(105);
        Builders.setLayoutY(130);
        Builders.setFill(PAINT_GRAY);
        Builders.setSmooth(false);

        // 获取用户程序正运行的 Java 版本
        Text UsersInfo = new Text("程序正运行 Java 版本为 " + System.getProperty("java.version"));
        UsersInfo.setLayoutX(105);
        UsersInfo.setLayoutY(200);
        UsersInfo.setFill(PAINT_GRAY);
        UsersInfo.setSmooth(false);

        // 关闭按钮
        Button close = new Button("");
        close.setPrefWidth(50);
        close.setPrefHeight(30);
        close.setBackground(
                new Background(new BackgroundImage(CLOSE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(25, 25, false, false, false, false))));
        HBox Title_Right_CLOSE = new HBox(close);
        Title_Right_CLOSE.setPrefWidth(50);
        Title_Right_CLOSE.setPrefHeight(30);
        Title_Right_CLOSE.setLayoutX(ABOUT_WIDTH - 50);
        Title_Right_CLOSE.setLayoutY(0);
        close.setOnMouseEntered(mouseEvent -> Title_Right_CLOSE.setBackground(BG_RED));
        close.setOnMouseExited(mouseEvent -> Title_Right_CLOSE.setBackground(null));
        close.setOnMousePressed(mouseEvent -> Title_Right_CLOSE.setBackground(BG_PINK));
        close.setOnMouseClicked(mouseEvent -> stage.close());

        // 标题底衬
        Label title = new Label("关于 " + APP_NAME);
        title.setFont(FONT_SC_NORMAL);
        title.setTextFill(Color.LIGHTGRAY);
        HBox Title = new HBox(title);
        Title.setPrefWidth(ABOUT_WIDTH);
        Title.setPrefHeight(30);
        Title.setBackground(BG_DARKER);
        Title.setAlignment(Pos.CENTER);
        Title.setOnMouseDragged(mouseEvent -> {
            // 拖动改变位置
            stage.setX(x_stage + mouseEvent.getScreenX() - x1);
            stage.setY(y_stage + mouseEvent.getScreenY() - y1);
        });
        Title.setOnMousePressed(mouseEvent -> {
            // 按下获取初始值
            x1 = mouseEvent.getScreenX();
            y1 = mouseEvent.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        });

        // 底衬
        HBox Body = new HBox();
        Body.setLayoutX(0);
        Body.setLayoutY(0);
        Body.setPrefWidth(ABOUT_WIDTH);
        Body.setPrefHeight(ABOUT_HEIGHT);
        Body.setBackground(BG_DARK);
        Body.setAlignment(Pos.CENTER);

        // 加组
        Group group = new Group();
        group.getChildren().add(Body);
        group.getChildren().add(Title);
        group.getChildren().add(Title_Right_CLOSE);
        group.getChildren().add(Big_Icon);
        group.getChildren().add(Name);
        group.getChildren().add(PubicVersion);
        group.getChildren().add(Builders);
        group.getChildren().add(UsersInfo);

        Scene scene = new Scene(group);
        scene.setFill(null);

        // 定位
        stage.setX((SCREEN_WIDTH - ABOUT_WIDTH) / 2);
        stage.setY((SCREEN_HEIGHT - ABOUT_HEIGHT) / 2);

        // 配置
        stage.setTitle("关于");
        stage.getIcons().add(ICON);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(ABOUT_WIDTH);
        stage.setHeight(ABOUT_HEIGHT);
        stage.setOpacity(0.9);

        // 布局
        stage.setScene(scene);

        // 显示
        stage.showAndWait();

    }
}

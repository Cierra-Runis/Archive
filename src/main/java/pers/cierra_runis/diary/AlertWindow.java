package pers.cierra_runis.diary;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static pers.cierra_runis.diary.SystemInfo.*;

/**
 * 这个 AlertWindow 类创建窗口显示消息并要求选择是或否。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class AlertWindow {

    /** 存储返回值 */
    private static boolean result = false;

    /**
     * 创建窗口显示消息并要求选择是或否。<br/>
     *
     * @return 用户选择的值
     * @author 8008121403
     */
    public static boolean display(String tit, String msg) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // 中部的提示文本
        Label label = new Label(msg);
        label.setFont(FONT_SC_REGULAR);
        label.setTextFill(PAINT_GRAY);
        HBox Label = new HBox(label);
        Label.setPrefWidth(DATE_WIDTH);
        Label.setPrefHeight(DATE_HEIGHT);
        Label.setBackground(BG_DARKER);
        Label.setAlignment(Pos.CENTER);

        // 左部的取消按钮
        Button cancel = new Button("");
        cancel.setPrefWidth(32);
        cancel.setPrefHeight(32);
        cancel.setAlignment(Pos.CENTER);
        cancel.setBackground(
                new Background(new BackgroundImage(CANCEL, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(32, 32, false, false, false, false))));
        HBox Cancel = new HBox(cancel);
        Cancel.setPrefWidth(20);
        Cancel.setPrefHeight(20);
        Cancel.setLayoutX(12);
        Cancel.setLayoutY(4);
        cancel.setOnMouseEntered(mouseEvent -> Cancel.setBackground(BG_DARK));
        cancel.setOnMouseExited(mouseEvent -> Cancel.setBackground(null));
        cancel.setOnMouseClicked(mouseEvent -> {
            // 按下取消按钮返回 false
            result = false;
            stage.close();
        });

        // 右部的确认按钮
        Button confirm = new Button("");
        confirm.setPrefWidth(25);
        confirm.setPrefHeight(25);
        confirm.setAlignment(Pos.CENTER);
        confirm.setBackground(
                new Background(new BackgroundImage(CONFIRM, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(20, 20, false, false, false, false))));
        HBox Confirm = new HBox(confirm);
        Confirm.setPrefWidth(20);
        Confirm.setPrefHeight(20);
        Confirm.setLayoutX(188);
        Confirm.setLayoutY(4);
        confirm.setOnMouseEntered(mouseEvent -> Confirm.setBackground(BG_DARK));
        confirm.setOnMouseExited(mouseEvent -> Confirm.setBackground(null));
        confirm.setOnMouseClicked(mouseEvent -> {
            // 按下确认按钮返回 true
            result = true;
            stage.close();
        });

        // 底衬
        HBox Body = new HBox();
        Body.setLayoutX(0);
        Body.setLayoutY(0);
        Body.setPrefWidth(DATE_WIDTH);
        Body.setPrefHeight(DATE_HEIGHT);
        Body.setBackground(BG_DARKER);
        Body.setAlignment(Pos.CENTER);

        // 加组
        Group group = new Group();
        group.getChildren().add(Body);
        group.getChildren().add(Label);
        group.getChildren().add(Cancel);
        group.getChildren().add(Confirm);

        Scene scene = new Scene(group);
        scene.setFill(null);

        // 定位
        stage.setX((SCREEN_WIDTH - DATE_WIDTH) / 2);
        stage.setY((SCREEN_HEIGHT - DATE_HEIGHT) / 2);

        // 配置
        stage.setTitle(tit);
        stage.getIcons().add(ICON);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(DATE_WIDTH);
        stage.setHeight(DATE_HEIGHT);
        stage.setOpacity(0.9);

        // 布局
        stage.setScene(scene);

        // 显示
        stage.showAndWait();

        // 关闭窗口后返回结果
        return result;

    }

}

package pers.cierra_runis.diary;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

import static pers.cierra_runis.diary.SystemInfo.*;

public class PasswordWindow {

    public static boolean result = false;

    public static boolean display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(PASSWORD_WIDTH);
        passwordField.setPrefHeight(PASSWORD_HEIGHT);
        passwordField.setFont(FONT_SC_REGULAR);
        passwordField.setPromptText("输入密码");
        passwordField.setBackground(BG_DARKER);
        passwordField.setStyle("-fx-text-fill: white; -fx-alignment: center");
        passwordField.setTooltip(new Tooltip("输入密码"));

        passwordField.setFocusTraversable(false);

        passwordField.textProperty().addListener((observableValue, s, t1) -> {
            if (t1.length() > 12) {
                passwordField.setText(s);
            }
        });

        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (Objects.equals(Base.stringToMD5(passwordField.getText()), PASSWORD)) {
                    result = true;
                    System.out.printf("输入字符串的 MD5 值为 %s，密码正确\n", Base.stringToMD5(passwordField.getText()));
                    stage.close();
                } else {
                    result = false;
                    System.out.printf("输入字符串的 MD5 值为 %s，密码错误\n", Base.stringToMD5(passwordField.getText()));
                    passwordField.setText("");
                }
            }
        });


        Group group = new Group();
        group.getChildren().add(passwordField);

        Scene scene = new Scene(group);
        scene.setFill(null);

        //定位
        stage.setX((SCREEN_WIDTH - PASSWORD_WIDTH) / 2);
        stage.setY((SCREEN_HEIGHT - PASSWORD_HEIGHT) / 2);

        //配置
        stage.setTitle("输入密码");
        stage.getIcons().add(ICON);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(PASSWORD_WIDTH);
        stage.setHeight(PASSWORD_HEIGHT);
        stage.setOpacity(0.9);

        //布局
        stage.setScene(scene);

        //显示
        stage.showAndWait();

        return result;
    }

}

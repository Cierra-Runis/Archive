package pers.cierra_runis.diary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

import static pers.cierra_runis.diary.SystemInfo.*;

/**
 * 这个 Editor 类创建窗口用于编辑日记内容。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class Editor {

    static double x1; // 用于移动页面的参数
    static double y1; // 用于移动页面的参数
    static double x_stage; // 用于移动页面的参数
    static double y_stage; // 用于移动页面的参数

    final Diary diaryInEditor; // Editor 类所操作的 Diary 类
    final String toDate; // 转移至的日期
    boolean edited = false; // 是否被编辑

    /**
     * 有参构造 Editor 类。</br>
     *
     * @param date 构造 Editor 类时传入的格式如 20220601 的日期字符串
     * @author 8008121403
     */
    public Editor(String date) {

        diaryInEditor = new Diary(date);
        diaryInEditor.readDiary();
        toDate = diaryInEditor.date;

    }

    /**
     * 创建窗口进行一个日记的编辑。</br>
     *
     * @return 是否被编辑
     * @author 8008121403
     */
    public boolean display() {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        // 右上保存按钮
        HBox save = new HBox();
        Text textInSaveButton = new Text("保存");
        textInSaveButton.setTextAlignment(TextAlignment.CENTER);
        textInSaveButton.setSmooth(true);
        textInSaveButton.setFont(new Font(FONT_SC_HEAVY.getName(), 15));
        textInSaveButton.setFill(PAINT_LIGHTBLUE);
        save.setPrefWidth(60);
        save.setPrefHeight(30);
        save.setLayoutX(EDITOR_WIDTH - 60 - 3);
        save.setLayoutY(3);
        save.setAlignment(Pos.CENTER);
        save.getChildren().add(textInSaveButton);

        // 左上取消按钮
        HBox cancel = new HBox();
        Text textInCancelButton = new Text("取消");
        textInCancelButton.setTextAlignment(TextAlignment.CENTER);
        textInCancelButton.setSmooth(true);
        textInCancelButton.setFont(new Font(FONT_SC_HEAVY.getName(), 15));
        textInCancelButton.setFill(PAINT_LIGHTBLUE);
        cancel.setPrefWidth(60);
        cancel.setPrefHeight(30);
        cancel.setLayoutX(3);
        cancel.setLayoutY(3);
        cancel.setAlignment(Pos.CENTER);
        cancel.getChildren().add(textInCancelButton);
        cancel.setOnMouseClicked(mouseEvent -> {
            // 按下取消按钮返回 false
            edited = false;
            stage.close();
        });

        // 右下修改日期按钮
        Button changeDate = new Button("");
        changeDate.setPrefWidth(30);
        changeDate.setPrefHeight(30);
        changeDate.setAlignment(Pos.CENTER);
        changeDate.setBackground(
                new Background(new BackgroundImage(DATE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(30, 30, false, false, false, false))));
        changeDate.setLayoutX(EDITOR_WIDTH - 135);
        changeDate.setLayoutY(EDITOR_HEIGHT - 35);
        changeDate.setOnMouseClicked(mouseEvent -> diaryInEditor.date = DateWindow.display());

        // 上部日记标题编辑区域
        TextField textField = new TextField(diaryInEditor.titleString);
        textField.setPrefWidth(150);
        textField.setPrefHeight(0.8 * DATE_HEIGHT);
        textField.setLayoutX((EDITOR_WIDTH - 150) / 2);
        textField.setLayoutY(3);
        textField.setBackground(BG_DARKER);
        textField.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        textField.setStyle("-fx-text-fill: rgb(138, 180, 248); -fx-alignment: center");
        textField.setFont(new Font(FONT_SC_NORMAL.getName(), 12));
        textField.setFocusTraversable(false);

        // 标题底衬
        HBox Title = new HBox(new Label());
        Title.setPrefWidth(EDITOR_WIDTH);
        Title.setPrefHeight(36);
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

        // 中部日记内容编辑区域
        TextArea textArea = new TextArea();
        textArea.setLayoutX(0.1 * EDITOR_WIDTH);
        textArea.setLayoutY(40);
        textArea.setPrefWidth(0.8 * EDITOR_WIDTH);
        textArea.setPrefHeight(EDITOR_HEIGHT - 40 - 40);
        textArea.setText(diaryInEditor.textString);
        textArea.setFocusTraversable(Objects.equals(textArea.getText(), "") || textArea.getText() == null);
        textArea.setWrapText(true);
        textArea.setBackground(BG_DARK);
        textArea.setBorder(new Border(new BorderStroke(PAINT_DARK, PAINT_DARK, PAINT_LIGHTDARK, PAINT_DARK,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        textArea.setStyle("-fx-text-fill: white");
        textArea.setFont(new Font(FONT_SC_NORMAL.getName(), 15));

        // 保存按钮的具体实现
        save.setOnMouseClicked(mouseEvent -> {
            if (Objects.equals(textField.getText(), "")) {
                textField.requestFocus();
            } else {
                diaryInEditor.time = Base.backTime();
                diaryInEditor.titleString = textField.getText();
                diaryInEditor.textString = textArea.getText();
                diaryInEditor.saveDiary();
                if (!Objects.equals(diaryInEditor.date, toDate)) {
                    diaryInEditor.transportDiary(toDate);
                }
                System.out.println("保存成功");
                edited = true;
                stage.close();
            }
        });

        // 底衬
        HBox Body = new HBox();
        Body.setPrefWidth(EDITOR_WIDTH);
        Body.setPrefHeight(EDITOR_HEIGHT);
        Body.setBackground(BG_DARK);

        // 加组
        Group group = new Group();
        group.getChildren().add(Body);
        group.getChildren().add(Title);
        group.getChildren().add(textArea);
        group.getChildren().add(textField);
        group.getChildren().add(save);
        group.getChildren().add(cancel);
        group.getChildren().add(changeDate);

        Scene scene = new Scene(group);
        scene.getStylesheets().add("file:resources/" + "pers/cierra_runis/diary/textarea.css"); // 使用 css 美化中部日记内容编辑区域
        scene.setFill(null);

        // 定位
        stage.setX((SCREEN_WIDTH - EDITOR_WIDTH) / 2);
        stage.setY((SCREEN_HEIGHT - EDITOR_HEIGHT) / 2);

        // 配置
        stage.setTitle("输入密码");
        stage.getIcons().add(ICON);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(EDITOR_WIDTH);
        stage.setHeight(EDITOR_HEIGHT);
        stage.setOpacity(0.9);

        // 布局
        stage.setScene(scene);

        // 显示
        stage.showAndWait();

        // 关闭窗口后返回结果
        return edited;

    }

}

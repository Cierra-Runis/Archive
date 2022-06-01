package pers.cierra_runis.diary;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;


public class DiaryApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        File dir = new File("diarys/");
        if (dir.mkdir()) {
            System.out.println("diarys 文件夹创建成功");
        } else if (dir.exists()) {
            System.out.println("diarys 文件夹已经存在");
        } else {
            System.out.println("diarys 文件夹创建失败");
        }

        if (PasswordWindow.display()) {
            new Page().start(stage);
        }

    }

}

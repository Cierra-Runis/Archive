package pers.cierra_runis.diary;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * 这个 DiaryApplication 类用于创建 diarys 文件夹并提示输入密码后进入主页面。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class DiaryApplication extends Application {

    /**
     * 启动程序。</br>
     *
     * @param args 不被使用
     * @author 8008121403
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * 开始程序。</br>
     *
     * @param stage 初始 stage 值
     * @author 8008121403
     */
    @Override
    public void start(Stage stage) {

        File dir = new File("database/");
        if (dir.mkdir()) {
            System.out.println("database 文件夹创建成功");
        } else if (dir.exists()) {
            System.out.println("database 文件夹已经存在");
        } else {
            System.out.println("database 文件夹创建失败");
        }

        if (PasswordWindow.display()) {
            new Page().start(stage);
        }

    }

}

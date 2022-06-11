package pers.cierra_runis.diary;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 * 这个 SystemInfo 类提供全局量辅助主程序。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class SystemInfo {

    static final Rectangle2D rectangle2D = Screen.getPrimary().getBounds();

    static final double SCREEN_WIDTH = rectangle2D.getWidth();
    static final double PASSWORD_WIDTH = 0.9 * SCREEN_WIDTH * 25 / 192;
    static final double HOMEPAGE_WIDTH = SCREEN_WIDTH * 160 / 192;
    static final double DATE_WIDTH = SCREEN_WIDTH * 25 / 192;
    static final double EDITOR_WIDTH = SCREEN_WIDTH * 120 / 192;
    static final double ABOUT_WIDTH = SCREEN_WIDTH * 527 / 1920;

    static final double SCREEN_HEIGHT = rectangle2D.getHeight();
    static final double PASSWORD_HEIGHT = 0.8 * SCREEN_HEIGHT * 5 / 108;
    static final double DATE_HEIGHT = PASSWORD_HEIGHT;
    static final double HOMEPAGE_HEIGHT = SCREEN_HEIGHT * 90 / 108;
    static final double EDITOR_HEIGHT = HOMEPAGE_HEIGHT * 0.8;
    static final double ABOUT_HEIGHT = HOMEPAGE_HEIGHT * 33 / 108;

    static final String APP_NAME = "我的日记";
    static final String APP_DATE = "2022.6.6";
    static final String APP_PUBLIC_VERSION = "1.0.0";
    static final String DEFAULT_DATE = "19000101";

    static final Image ICON = new Image("file:resources/" + "icon/Icon.png");
    static final Image SETTING = new Image("file:resources/" + "icon/Setting.png");
    static final Image MINIMIZE = new Image("file:resources/" + "icon/Minimize.png");
    static final Image CLOSE = new Image("file:resources/" + "icon/Close.png");
    static final Image TIME = new Image("file:resources/" + "icon/Time.png");
    static final Image DELETE_UNPRESSED = new Image("file:resources/" + "icon/Delete_Unpressed.png");
    static final Image DELETE_PRESSED = new Image("file:resources/" + "icon/Delete_Pressed.png");
    static final Image ADD_UNPRESSED = new Image("file:resources/" + "icon/Add_Unpressed.png");
    static final Image ADD_PRESSED = new Image("file:resources/" + "icon/Add_Pressed.png");
    static final Image DATE = new Image("file:resources/" + "icon/Date.png");
    static final Image CONFIRM = new Image("file:resources/" + "icon/Confirm.png");
    static final Image EDIT_UNPRESSED = new Image("file:resources/" + "icon/Edit_Unpressed.png");
    static final Image EDIT_PRESSED = new Image("file:resources/" + "icon/Edit_Pressed.png");
    static final Image CANCEL = new Image("file:resources/" + "icon/Cancel.png");
    static final Image SORTUP_PRESSED = new Image("file:resources/" + "icon/SortUp_Pressed.png");
    static final Image SORTUP_UNPRESSED = new Image("file:resources/" + "icon/SortUp_Unpressed.png");
    static final Image SORTDOWN_PRESSED = new Image("file:resources/" + "icon/SortDown_Pressed.png");
    static final Image SORTDOWN_UNPRESSED = new Image("file:resources/" + "icon/SortDown_Unpressed.png");
    static final Image ABOUT = new Image("file:resources/" + "icon/About.png");

    static final Font FONT_SC_BOLD = Font.loadFont("file:resources/" + "font/SourceHanSansCN-Bold.ttf", 12);
    static final Font FONT_SC_HEAVY = Font.loadFont("file:resources/" + "font/SourceHanSansCN-Heavy.ttf", 12);
    static final Font FONT_SC_NORMAL = Font.loadFont("file:resources/" + "font/SourceHanSansCN-Normal.ttf", 12);
    static final Font FONT_SC_REGULAR = Font.loadFont("file:resources/" + "font/SourceHanSansCN-Regular.ttf", 12);

    static final Paint PAINT_DARK = Paint.valueOf("#333333");
    static final Paint PAINT_DARKER = Paint.valueOf("#21252B");
    static final Paint PAINT_RED = Paint.valueOf("#E81123");
    static final Paint PAINT_PINK = Paint.valueOf("#F1707A");
    static final Paint PAINT_GRAY = Paint.valueOf("#DDDDDD");
    static final Paint PAINT_LIGHTDARK = Paint.valueOf("#666666");
    static final Paint PAINT_LIGHTBLUE = Paint.valueOf("#8AB4F8");
    static final Paint PAINT_CARD = Paint.valueOf("#404040");

    static final Background BG_DARK = new Background(new BackgroundFill(PAINT_DARK, new CornerRadii(8), new Insets(0)));

    static final Background BG_DARKER = new Background(new BackgroundFill(PAINT_DARKER, new CornerRadii(8), new Insets(0)));

    static final Background BG_RED = new Background(new BackgroundFill(PAINT_RED, new CornerRadii(8), new Insets(0)));

    static final Background BG_PINK = new Background(new BackgroundFill(PAINT_PINK, new CornerRadii(8), new Insets(0)));

    static final Background BG_GRAY = new Background(new BackgroundFill(PAINT_GRAY, new CornerRadii(8), new Insets(0)));

    static final Background BG_CARD = new Background(new BackgroundFill(PAINT_CARD, new CornerRadii(8), new Insets(0)));

    //未来和 info.json 数据库结合
    static String PASSWORD = Base.stringToMD5("8008121403");
    static String MOTTO = "好学习是为了不听讲";
    static boolean newToOld = true;
    static String USER_NAME = "Cierra_Runis";
    static Image PROFILE_PHOTO = new Image("file:users/Profile_photo.jpg");

}

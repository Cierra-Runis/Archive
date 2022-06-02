package pers.cierra_runis.diary;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 这个 Base 类提供部分实用方法辅助主程序。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class Base {

    /**
     * 判断是否符合如 197001010000 的时间戳格式。</br>
     *
     * @param dateStr 需判断的时间戳字符串
     * @return 返回判断结果
     * @author 8008121403
     */
    public static boolean isTimeStamp(String dateStr) {

        String timeRegex = "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|" +
                "((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))" +
                "([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$";
        return Pattern.matches(timeRegex, dateStr);

    }

    /**
     * 判断是否包含符合如 [11:45:14] 的时间标签格式。</br>
     *
     * @param dataStr 需判断是否包含时间标签的字符串
     * @return 返回判断结果
     * @author 8008121403
     */
    public static boolean isTimeLabel(String dataStr) {

        String timeLabelRegex = "^.*\\[(?:[01]\\d|2[0-3])(?::[0-5]\\d){2}].*$";
        return Pattern.matches(timeLabelRegex, dataStr);

    }

    /**
     * 由日期判断该日是星期几。</br>
     *
     * @param datetime 如 2022-06-01 格式的日期
     * @return 返回 星期一 至 星期日 中的一个
     * @author 8008121403
     */
    public static String dateToWeek(String datetime) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = f.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(Objects.requireNonNull(date));
        //一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 用于删除文件（夹）。</br>
     *
     * @param file 需删除的文件（夹）
     * @return 是否成功删除
     * @author 8008121403
     */
    public static boolean delFiles(File file) {

        boolean result;
        if (file.isDirectory()) {
            File[] childrenFiles = file.listFiles();

            for (File childFile : Objects.requireNonNull(childrenFiles)) {
                result = delFiles(childFile);
                if (!result) {
                    return false;
                }
            }
        }
        result = file.delete();
        return result;
    }

    /**
     * 判断传入的字符串是否为某位数。</br>
     *
     * @param str    需要判断的字符串
     * @param length 位数
     * @return 返回判断结果
     * @author 8008121403
     */
    public static boolean isIntNumber(String str, int length) {

        try {
            int num = Integer.parseInt(str);
            return String.valueOf(num).length() == length;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 由年份和月份判断该月有多少天。</br>
     *
     * @param year  年份
     * @param month 月份
     * @return 天数
     * @author 8008121403
     */
    public static int getDayOfMonth(int year, int month) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获取方法调用时的时间。</br>
     *
     * @return 如 213030 的时分秒字符串
     * @author 8008121403
     */
    public static String backTime() {

        Calendar calendar = Calendar.getInstance();

        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        if (second.length() == 1) {
            second = "0" + second;
        }

        return hour + minute + second;
    }

    /**
     * 判断日期对应日记是否已经存在。</br>
     *
     * @param date 如 20220601 格式的日期
     * @return 返回判断结果
     * @author 8008121403
     */
    public static boolean isDateExisted(String date) {

        File file = new File("diarys/");
        File[] files = file.listFiles();

        for (File f : Objects.requireNonNull(files)) {
            if (f.isDirectory() && Objects.equals(date, f.getName().substring(0, 8))) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取 diarys 文件夹下的所有日记并读取，返回所构成的 Diary 数组。</br>
     *
     * @return 返回所构成的 Diary 数组
     * @author 8008121403
     */
    public static Diary[] getAllDiary() {

        File diarysFile = new File("diarys/");
        File[] filesInDiarysFile = diarysFile.listFiles();

        if (filesInDiarysFile != null && filesInDiarysFile.length != 0) {
            System.out.print("\n正在读取所有日记\n");
            Diary[] diaries = new Diary[filesInDiarysFile.length];
            for (int i = 0; i < filesInDiarysFile.length; i++) {
                diaries[i] = new Diary(filesInDiarysFile[i].getName().substring(0, 8));
                diaries[i].readDiary();
            }
            System.out.println();
            return diaries;
        } else {
            return null;
        }

    }

    /**
     * 使用 MD5 方法加密字符串
     *
     * @param plainText 加密前的字符串
     * @return 返回加密后的字符串
     * @author 8008121403
     */
    public static String stringToMD5(String plainText) {

        byte[] secretBytes = new byte[0];
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();

    }

}

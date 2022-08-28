package pers.cierra_runis.diary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 这个 Diary 类主要用于 json 文件的操作。<br/>
 *
 * @author 8008121403
 * @author 8008121407
 * @version 1.0.0
 */
public class Diary {
    /**
     * 年月日，例如 19000101
     */
    public String date;
    /**
     * 时间，例如 0000
     */
    public String time = "0000";
    /**
     * 标题字符串
     */
    public String titleString = "无标题";
    /**
     * 文本字符串直接储存所有文本信息
     */
    public String textString = "";

    // 这里要求 date 合法
    public Diary(String date) {
        this.date = date;
    }

    /**
     * 存储这个类对应 diarys.json 中的所有信息。<br/>
     *
     * @author 8008121403
     */
    public void saveDiary(String time) {

        List<Diary> diaryList = Base.getDiaryList();

        // 如果这个类的 date 已经存在，那么我们删去原先的
        // 对于 添加日记 已在 DateWindow 保证上述情形不存在，这是为了 编辑日记 而编写的
        if (Base.isDateExisted(date)) {
            System.out.printf("所想保存日记的 timeStamp (%s) 对应的 date 已经存在，正在删除原先日记\n", date + time);
            deleteDiary();
        }

        Diary diary = new Diary(date);
        diary.titleString = this.titleString;
        diary.textString = this.textString;
        diary.time = time;
        Objects.requireNonNull(diaryList).add(diary);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String str = gson.toJson(diaryList);
        File dir = new File("database/diarys.json");
        try {
            Files.writeString(dir.toPath(), str, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 删除这个类对应 diarys.json 中的所有信息。<br/>
     *
     * @author 8008121403
     */
    public void deleteDiary() {

        List<Diary> diaryList = Base.getDiaryList();

        for (Diary diary : Objects.requireNonNull(diaryList)) {
            // 当日期对的上时
            if (Objects.equals(diary.date, date)) {
                diaryList.remove(diary);
            }
        }

        Base.saveDiaryList(diaryList);

    }

    /**
     * 修改这个类对应 diarys.json 中的所有信息。<br/>
     *
     * @param toDate 转移至的日期
     * @author 8008121403
     */
    public void transportDiary(String toDate) {

        List<Diary> diaryList = Base.getDiaryList();

        for (Diary diary : Objects.requireNonNull(diaryList)) {
            // 当日期对的上时
            if (Objects.equals(diary.date, date)) {
                diary.date = toDate;
            }
        }

        Base.saveDiaryList(diaryList);

    }

    /**
     * 将这个类中文本字符串按换行切片。<br/>
     *
     * @return 按换行切片后的字符串数组
     * @author 8008121403
     */
    public String[] textToStrings() {
        return textString.split("\n");
    }

    /**
     * 将传入的 json 文件解析为 diarys.json 的形式存储。<br/>
     *
     * @author 8008121403
     */
    public static void jsonToFiles(File jsonFile) {

        List<Nideriji> niderijiList;
        List<Diary> diaryList = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            niderijiList = gson.fromJson(Files.readString(jsonFile.toPath()), new TypeToken<List<Nideriji>>() {
            }.getType());
            for (Nideriji nideriji : niderijiList) {
                // 删除日期不是 None 且内容是 deleted 的话
                if (!Objects.equals(nideriji.deleteddate, "None") && Objects.equals(nideriji.Content, "deleted")) {
                    System.out.println("该日（" + nideriji.createddate + "）的日记已于 " + nideriji.deleteddate + " 删除");
                } else {
                    System.out.println("该日（" + nideriji.createddate + "）的日记未删除且最近更新于 " + nideriji.Ts);
                    String date = nideriji.createddate.substring(0, 4) + nideriji.createddate.substring(5, 7) + nideriji.createddate.substring(8, 10);
                    String time = nideriji.Ts.substring(11, 13) + nideriji.Ts.substring(14, 16) + nideriji.Ts.substring(17, 19);
                    Diary diary = new Diary(date);
                    diary.time = time;
                    diary.textString = nideriji.Content;
                    diary.titleString = Objects.equals(nideriji.Title, "") ? "无标题" : nideriji.Title;
                    diaryList.add(diary);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Base.saveDiaryList(diaryList);

    }

    public static int backIndex(String date) {

        List<Diary> diaryList = Base.getDiaryList();

        for (Diary diary : Objects.requireNonNull(diaryList)) {
            if (Objects.equals(diary.date, date)) {
                return diaryList.indexOf(diary);
            }
        }
        return -1;

    }

}

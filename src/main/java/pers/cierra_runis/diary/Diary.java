package pers.cierra_runis.diary;

import java.io.*;
import java.util.Objects;

/**
 * 这个 Diary 类主要用于文件操作。<br/>
 *
 * @author 8008121403
 * @version 1.0.0
 */
public class Diary {

    String date;                                    //date 年月日，例如 19000101
    String time = "0000";                           //time 时间，例如 0000
    String titleString = "无标题";                   //titleString 标题字符串
    String textString = "";                         //textString 文本字符串直接储存所有文本信息

    //这里要求 date 合法
    public Diary(String date) {
        this.date = date;
    }

    /**
     * 存储这个类对应文件夹中的所有信息。<br/>
     *
     * @author 8008121403
     */
    public void saveDiary() {

        //如果这个类的 date 已经存在，那么我们删去原先的
        //对于 添加日记 已在 DateWindow 保证上述情形不存在，这是为了 编辑日记 而编写的
        if (Base.isDateExisted(date)) {
            System.out.printf("所想保存日记的 timeStamp (%s) 对应的 date 已经存在，正在删除原先日记\n", date + time);
            deleteDiary();
        }

        time = Base.backTime();
        File dir = new File("diarys/" + date + time);
        if (dir.mkdir()) {
            System.out.printf("%s 的文件夹已于 %s 创建成功\n", date, time);
        }
        File file = new File("diarys/" + date + time + "/" + titleString + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(textString);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除这个类对应文件夹中的所有信息。<br/>
     *
     * @author 8008121403
     */
    public void deleteDiary() {

        File diarysFile = new File("diarys/");
        File[] filesInDiarysFile = diarysFile.listFiles();

        for (File file : Objects.requireNonNull(filesInDiarysFile)) {
            //当 file 是文件夹且 date 对的上时
            if (file.isDirectory() && file.getName().substring(0, 8).equals(date)) {
                System.out.printf("已删除 %s 文件夹\n", file.getName());
                Base.delFiles(file);
            }
        }

    }

    /**
     * 读取这个类对应文件夹中的所有信息。<br/>
     *
     * @author 8008121403
     */
    public void readDiary() {

        File diarysFile = new File("diarys/");
        File[] filesInDiarysFile = diarysFile.listFiles();

        for (File file : Objects.requireNonNull(filesInDiarysFile)) {
            //当 file 是文件夹且 date 对的上时
            if (file.isDirectory() && file.getName().substring(0, 8).equals(date)) {

                System.out.printf("已找到 %s 文件夹，正在读取\n", file.getName());
                time = file.getName().substring(file.getName().length() - 6);
                File[] files = file.listFiles();

                for (File f : Objects.requireNonNull(files)) {
                    if (f.getName().endsWith(".txt")) {
                        titleString = f.getName().substring(0, f.getName().length() - 4);
                        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                textString = textString.concat(line + "\n");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.printf("哪来的 \"%s\" 垃圾？\n", f.getName());
                    }
                }

            }
        }
    }

    /**
     * 转移这个类对应文件夹中的所有信息。<br/>
     *
     * @param toDate 转移至的日期
     * @author 8008121403
     */
    public void transportDiary(String toDate) {

        File diarysFile = new File("diarys/");
        File[] filesInDiarysFile = diarysFile.listFiles();

        for (File file : Objects.requireNonNull(filesInDiarysFile)) {
            //当 file 是文件夹且 date 对的上时
            if (file.isDirectory() && file.getName().substring(0, 8).equals(date)) {

                //Q: 这里逻辑我晕了
                Diary newDiary = new Diary(date);
                newDiary.titleString = titleString;
                newDiary.textString = textString;
                newDiary.saveDiary();
                new Diary(toDate).deleteDiary();

                System.out.printf("%s 已转移至 %s\n", date, toDate);
            }
        }

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

}

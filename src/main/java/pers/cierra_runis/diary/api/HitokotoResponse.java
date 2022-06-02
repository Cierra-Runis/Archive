package pers.cierra_runis.diary.api;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 这个 HitokotoResponse 类从 https://v1.hitokoto.cn 获取并解析 json。<br/>
 * 网络相关由 谢佬（https://github.com/WOo0W） 提供帮助。<br/>
 *
 * @author 8008121403
 * @author 8008121407
 * @version 1.0.0
 */
public class HitokotoResponse {

    public String hitokoto;                         //hitokoto API 返回的句子内容
    public String from;                             //hitokoto API 返回的句子来源
    public String uuid;                             //hitokoto API 返回的 uuid，用于网站指引

    /**
     * 从 https://v1.hitokoto.cn 获取获取信息
     *
     * @return 包含有信息的 HitokotoResponse 类
     * @throws Exception 若获取失败
     * @author 8008121407
     */
    public static HitokotoResponse getHitokoto() throws Exception {

        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().uri(URI.create("https://v1.hitokoto.cn")).GET().build();
        var gson = new Gson();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var body = response.body();
        return gson.fromJson(body, HitokotoResponse.class);

    }

}

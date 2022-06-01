package pers.cierra_runis.diary.api;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


//网络相关由 谢佬（https://github.com/WOo0W） 提供帮助
public class HitokotoResponse {
    public String hitokoto;
    public String from;
    public String uuid;

    public static HitokotoResponse getHitokoto() throws Exception {
        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder().uri(URI.create("https://v1.hitokoto.cn")).GET().build();
        var gson = new Gson();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var body = response.body();
        return gson.fromJson(body, HitokotoResponse.class);
    }

}

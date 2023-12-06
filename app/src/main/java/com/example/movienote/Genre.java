package com.example.movienote;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Genre extends Fragment {

    public static String movieSearch(String input) {
        if (input == null) {
            return null;
        }

        try {
            // API 엔드포인트 및 파라미터 설정
            String apiUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp";
            String apiKey = "V40N5WM77MESM46PM90Y";
            String queryParam = URLEncoder.encode("genre", "UTF-8") + "=" + URLEncoder.encode(input, "UTF-8");

            // URL 생성
            URL url = new URL(apiUrl + "?collection=kmdb_new2&ServiceKey=" + apiKey + "&query=" + queryParam);
            Log.d("KDE", url.toString());

            // HTTP 연결 설정
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            // 응답 코드 확인
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                // 응답 데이터 읽기
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }

                    // 입력 문자열 수정
                    String sbString = sb.toString();
                    sbString = sbString.replace("\"" + input + "\"", input);

                    return sbString;
                }
            } else {
                // 에러 응답 처리
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        errorResponse.append(line);
                    }

                    Log.e("MovieSearchActivity", "Error response: " + errorResponse.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String parseJson(String jsonData) {
        String movies = "";

        if (jsonData != null) {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
                JsonArray data = jsonObject.getAsJsonArray("Data");

                JsonObject dataParsing = data.get(0).getAsJsonObject();
                JsonArray result = dataParsing.getAsJsonArray("Result");
                //Log.d("result", String.valueOf(result));

                for (JsonElement element : result) {
                    JsonObject resultObject = element.getAsJsonObject();

                    //Log.d("title",title);
                    String posterUrl = resultObject.get("posters").getAsString();
                    String[] poster = posterUrl.split("\\|");
                    MovieItem movie;
                    if (poster[0].length()==0){
                        return "https://ifh.cc/g/MJ7jPL.png";
                    }else{
                        return poster[0];
                    }

                }
            } catch (JsonParseException e) {
                e.printStackTrace();
                Log.e("LSY", "JsonParseException: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LSY", "Exception: " + e.getMessage());
            }}else {
            Log.e("MovieSearchActivity", "JSON data is null");
        }

        return movies;
    }
}

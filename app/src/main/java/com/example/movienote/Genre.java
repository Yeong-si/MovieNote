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
            String queryParam = URLEncoder.encode("genre", "UTF-8") + "="  + URLEncoder.encode("\"", "UTF-8") + URLEncoder.encode(input, "UTF-8") + URLEncoder.encode("\"", "UTF-8");

            // URL 생성
            URL url = new URL(apiUrl + "?collection=kmdb_new2&listCount=800&ServiceKey=" + apiKey + "&query=" + queryParam);
//            Log.d("KDE", "url : " + url.toString());

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
}

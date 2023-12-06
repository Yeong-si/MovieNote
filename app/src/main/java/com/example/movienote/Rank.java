package com.example.movienote;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Rank {
    public static String format_yyyyMMdd = "yyyyMMdd";

    public static String[] movieRanking() {
        try {
            // API 엔드포인트 및 파라미터 설정
            String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json";
            String apiKey = "b3a6451ff29da585a790ee3a45882a17";
            Date currentTime = Calendar.getInstance().getTime();
            Date resultDate = new Date(currentTime.getTime() - (1000 * 60 * 60 * 24 * 8));
            SimpleDateFormat format = new SimpleDateFormat(format_yyyyMMdd, Locale.getDefault());
            String current = format.format(resultDate);
            String targetDate = URLEncoder.encode(current, "UTF-8");

            // URL 생성
            URL url = new URL(apiUrl + "?key=" + apiKey + "&weekGb=0&targetDt=" + targetDate);
            Log.d("KDE", "ranking url : " + url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;


            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String[] output = new String[2];
            output = parseJson(sb.toString());
            Log.d("KDE","1위 : " + output[0]);
            Log.d("KDE",output[1]);
            Log.d("KDE",output[2]);


            return output;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] parseJson(String jsonData) {
        String jsonString = jsonData;
        List<String> topMovies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject boxOfficeResult = jsonObject.getJSONObject("boxOfficeResult");
            JSONArray weeklyBoxOfficeList = boxOfficeResult.getJSONArray("weeklyBoxOfficeList");

            if (weeklyBoxOfficeList != null) {
                // 1, 2, 3위 영화 제목을 배열로 가져오기
                topMovies = getTopMovies(weeklyBoxOfficeList, 3);
            } else {
                // Handle the case where the JsonArray is null (e.g., provide a default value)
                // For now, let's just log a message
                Log.e("KDE", "weeklyBoxOfficeList is null");
            }
        } catch (JSONException e) {
            Log.d("KDE", e.getMessage());
        }
        return topMovies.toArray(new String[0]);
    }

    private static List<String> getTopMovies(JSONArray movieArray, int count) throws JSONException {
        List<String> topMovies = new ArrayList<>();

        for (int i = 0; i < count && i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            String movieNm = movie.getString("movieNm");
            topMovies.add(movieNm);
        }

        return topMovies;
    }
}

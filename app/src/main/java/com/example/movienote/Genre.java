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

    public static String MovieSearch(String input) {

        if (input == null) {
            return null;
        }
        try {
            StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&ServiceKey=");
            urlBuilder.append(URLEncoder.encode("V40N5WM77MESM46PM90Y", "UTF-8"));/*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("genre", "UTF-8") + "=" + URLEncoder.encode(input, "UTF-8"));
            URL url = null;
            try {
                url = new URL(urlBuilder.toString());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

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

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

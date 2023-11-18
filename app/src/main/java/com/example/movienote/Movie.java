package com.example.movienote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Movie extends Fragment {

    public static void main(String[] args ){
        String clientId = "v5dYiYPykGjNM7t62YrS";
        String clientSecret = "4VsYk4ZFhp";

        String text=null;
        int display = 100;
        try {
            text = URLEncoder.encode("영화제목","UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json"+text+"&display="+display+"&";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id",clientId);
            con.setRequestProperty("X-Naver-Client-Secret",clientSecret);
            int reponseCode = con.getResponseCode();
            BufferedReader br;
            if (reponseCode == 200){
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            }else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer reponse = new StringBuffer();
            while((inputLine = br.readLine()) != null){
                reponse.append(inputLine);
                reponse.append("\n");
            }
            br.close();
            con.disconnect();

            String naverHtml = reponse.toString();
            Bundle bun = new Bundle();
            bun.putString("NAVER_HTML", naverHtml);



        }catch (Exception e){
            e.printStackTrace();
        }


    }

}

package com.example;
//this is the code take the data from internet

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class USDAApiCall {
    public static String searchFood(String foodName) {
        String apiKey = "XzSGqAN82PJZMxpcMxZYv0bpyqxqqBJ4zGO6n6PU";  
        String urlString = "https://api.nal.usda.gov/fdc/v1/foods/search?query=" + foodName + "&api_key=" + apiKey;

        StringBuilder result = new StringBuilder();
        try {
            //javafx application connection to usda api server
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString(); //return data in json format
    }
}


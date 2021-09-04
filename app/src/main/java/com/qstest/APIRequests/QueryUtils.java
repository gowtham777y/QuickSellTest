package com.qstest.APIRequests;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static String LOG_TAG = QueryUtils.class.getSimpleName();

    private static URL createURL(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error creating URL",e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonresponse = "";
        if (url == null){
            return jsonresponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonresponse = readfromstream(inputStream);
            }

            else {
                Log.e(LOG_TAG,"Error Response Code:"+urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG,"Problem Retreving products from JSON Response",e);
        } finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonresponse;
    }

    private static String readfromstream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<String> extractFeaturesfromJson(String jsonresponse){
        List<String> products=new ArrayList<>();

        if (jsonresponse == null){
            return products;
        }

        try{
//            JSONObject jsonObject=new JSONObject(jsonresponse);
//            JSONArray results= jsonObject.getJSONArray("");
            JSONArray results = new JSONArray(jsonresponse);
            for (int i=0;i<results.length();i++){
                String product = results.getString(i);
//                String product = (String) result.toString();
                products.add(product);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Error creating JSONObject",e);
        }

        return products;
    }

    public static List<String> fetchData(String urlString){
        URL url = createURL(urlString);

        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error closing input Stream",e);
        }

        List<String> products = extractFeaturesfromJson(jsonResponse);

        return products;
    }
}

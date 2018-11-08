package com.betherichest.android.mangers;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class ConnectionManager extends AsyncTask<URL, String, Void> {
    private URL url;
    private Map<String, Object> requestParams;
    private Map<String, String> headerParams;
    public static final String BTR_URL = "https://betherichest-1994.appspot.com";

    public ConnectionManager(URL url, Map<String, Object> requestParams) {
        this.url = url;
        this.requestParams = requestParams;
        execute();
    }

    /**
     * @param requestParams key-value pair request parameters
     * */
    public ConnectionManager(URL url, Map<String, Object> requestParams, Map<String, String> headerParams) {
        this.url = url;
        this.requestParams = requestParams;
        this.headerParams = headerParams;
        execute();
    }

    @Override
    protected Void doInBackground(URL... urls) {
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : requestParams.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), System.getProperty("file.encoding")));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), System.getProperty("file.encoding")));
            }
            byte[] postDataBytes = postData.toString().getBytes(System.getProperty("file.encoding"));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (headerParams != null){
                for (Map.Entry<String, String> param : headerParams.entrySet()) {
                    conn.setRequestProperty(param.getKey(), param.getValue());
                }
            }
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            System.out.println("Response Code : " + conn.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            in.close();

            System.out.println(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

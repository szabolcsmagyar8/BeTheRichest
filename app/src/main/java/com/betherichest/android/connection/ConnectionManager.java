package com.betherichest.android.connection;

import android.os.AsyncTask;

import com.betherichest.android.activities.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ConnectionManager extends AsyncTask<URL, String, Void> {
    public static Queue<RequestItem> requestItems = new LinkedList<>();
    private URL url;
    private Map<String, Object> requestParams;
    private Map<String, String> headerParams = new HashMap<>();
    private HTTPMethod httpMethod;
    private ActionType actionType;
    public static final String BTR_URL = "https://betherichest-1994.appspot.com";
    private String endpoint;


    /**
     * @param endpoint
     * @param requestParams key-value pair request parameters
     * @param actionType
     */
    public ConnectionManager(String endpoint, Map<String, Object> requestParams, HTTPMethod httpMethod, ActionType actionType) throws MalformedURLException {
        this.endpoint = endpoint;
        this.url = new URL(BTR_URL + endpoint);
        headerParams.put("Authorization", LoginActivity.BEARER_TOKEN);
        this.requestParams = requestParams;
        this.httpMethod = httpMethod;
        this.actionType = actionType;
        execute();
    }

    @Override
    protected Void doInBackground(URL... urls) {
        try {
            byte[] postDataBytes = convertParamsToBytes();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (headerParams != null) {
                for (Map.Entry<String, String> param : headerParams.entrySet()) {
                    conn.setRequestProperty(param.getKey(), param.getValue());
                }
            }
            conn.setRequestMethod(httpMethod.name());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            printResponse(conn);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            requestItems.add(new RequestItem(endpoint, requestParams, actionType));
        }
        return null;
    }

    private byte[] convertParamsToBytes() throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        String encoding = System.getProperty("file.encoding");

        for (Map.Entry<String, Object> param : requestParams.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), encoding));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), encoding));
        }
        return postData.toString().getBytes(encoding);
    }

    private void printResponse(HttpURLConnection conn) throws IOException {
        System.out.println("Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());

        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException exception) {
            inputStream = conn.getErrorStream();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        response.append("---------------------> OUTPUT: ");
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
            if (actionType == ActionType.LOGIN) {
                output = output.replace("\"", "");
                LoginActivity.BEARER_TOKEN = "Bearer " + output;
            }
        }
        System.out.println(response.toString());
        inputStream.close();
    }
}

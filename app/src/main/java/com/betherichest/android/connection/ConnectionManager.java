package com.betherichest.android.connection;

import android.os.AsyncTask;

import com.betherichest.android.activities.LoginActivity;
import com.betherichest.android.database.DatabaseManager;

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
import java.util.List;
import java.util.Map;

public class ConnectionManager extends AsyncTask<URL, String, Void> {
    public static final String BTR_URL = "http://krisz094.asuscomm.com";
    public static final String AUTHORIZATION = "Authorization";
    private URL url;
    public static List<RequestItem> requestItems = new LinkedList<>();
    private Map<String, String> headerParams = new HashMap<>();
    private HTTPMethod httpMethod;
    private ActionType actionType;
    private RequestItem requestItem;
    private List<RequestParam> requestParams;
    private String endpoint;

    public ConnectionManager(RequestItem requestItem) throws MalformedURLException {
        this.requestItem = requestItem;
        endpoint = requestItem.getEndPoint();
        url = new URL(BTR_URL + endpoint);
        headerParams.put(AUTHORIZATION, LoginActivity.BEARER_TOKEN);
        requestParams = requestItem.getRequestParams();
        httpMethod = requestItem.getActionType() == ActionType.LOG || requestItem.getActionType() == ActionType.LOGIN ? HTTPMethod.POST : HTTPMethod.GET;
        actionType = requestItem.getActionType();
        execute();
    }

    @Override
    protected Void doInBackground(URL... urls) {
        try {
            byte[] postDataBytes = convertParamsToBytes();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty(AUTHORIZATION, headerParams.get(AUTHORIZATION));
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
            requestItems.add(requestItem);
        }
        return null;
    }

    private byte[] convertParamsToBytes() throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        String encoding = System.getProperty("file.encoding");

        for (RequestParam param : requestParams) {
            if (postData.length() != 0) {
                postData.append('&');
            }
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
        requestItems.remove(requestItem);
        DatabaseManager.instance.removeRequestItem(requestItem);
        inputStream.close();
    }
}

package com.betherichest.android.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by krisz on 2018. 03. 10..
 */

public class Communicator {

    private class NetworkMessage {
        String message;
        String date;
        public NetworkMessage(String message) {
            this.message = message;
            this.date = String.valueOf(System.currentTimeMillis());
        }
    }

    //private ConnectivityManager cm;
   // private Context ctx;
   // private BroadcastReceiver br;
    private OkHttpClient httpClient;
    private Gson gson;
    private String APIRoot;

    private static final MediaType JSONType = MediaType.parse("application/json; charset=utf-8");

    public Communicator( String APIRoot){
        //this.ctx = ctx;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
        this.APIRoot = APIRoot;
        /*br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        ctx.registerReceiver(br,"ASD");*/
    }

    private class SendReqestTask extends AsyncTask<Request, String, String> {
        @Override
        protected String doInBackground(Request... requests) {
                Request r = requests[0];
                try {
                    Response response = httpClient.newCall(r).execute();
                    return response.body().string();
                } catch (IOException e) {
                    return "ERROR";
                }

        }
    }

    public String POSTMessage(String msg, String endpoint) {
        NetworkMessage netm = new NetworkMessage(msg);
        String JSON = gson.toJson(netm);

        RequestBody body = RequestBody.create(JSONType, JSON);
        Request request = new Request.Builder()
                .url(combinePath(endpoint))
                .post(body)
                .build();
        try{
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "ERROR";
        }
    }

    private HttpUrl combinePath(String endpoint) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(APIRoot)
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment(endpoint)
                .build();
    }

    public String GETEndpoint(String endpoint) {

        Request request = new Request.Builder()
                .url(combinePath(endpoint))
                .get()
                .build();


        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("meme", "onFailure: " + e.getMessage());
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("valasz", "onResponse: " + response.body().string());
            }
        });

        return "a";
    }


}

package com.betherichest.android;

import java.io.IOException;
import java.io.InputStream;

public class JsonManager {
    public static String getJsonFromResource(int resource) {
        String json;
        try {
            InputStream stream = App.getContext().getResources().openRawResource(resource);
            int size = 0;
            try {
                size = stream.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

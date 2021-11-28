package org.SpecikMan.Tools;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class crudAPI {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String get(String urlAPI) throws IOException {
        Request request = new Request.Builder()
                .url(urlAPI)
                .addHeader("custom-key", "SpecikMan")  // add request headers
                .addHeader("User-Agent", "TypeMaster")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            assert response.body() != null;
            return response.body().string();
        }
    }
    public static void post(JSONObject json, String urlAPI) throws Exception {
        RequestBody body = RequestBody.create(JSON, json.toString());
        // form parameters
        Request request = new Request.Builder()
                .url(urlAPI)
                .addHeader("User-Agent", "TypeMaster")
                .post(body)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void put(JSONObject json,String urlAPI) throws Exception {
        RequestBody body = RequestBody.create(JSON, json.toString());
        // form parameters
        Request request = new Request.Builder()
                .url(urlAPI)
                .addHeader("User-Agent", "TypeMaster")
                .put(body)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void delete(String urlAPI) throws Exception {
        // form parameters
        Request request = new Request.Builder()
                .url(urlAPI)
                .addHeader("User-Agent", "TypeMaster")
                .delete()
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

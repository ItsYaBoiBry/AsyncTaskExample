package com.bryan.asynctaskexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncActivityClass extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_class);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        getJSONString results = new getJSONString();
        results.execute(link);
    }

    private class getJSONString extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urls[0]).header("AccountKey", "SFtHKwbETzi/0lasguUW5g==").build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ERROR : ", e.getLocalizedMessage());
            }
            if (response.isSuccessful()) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("executed result: ", result + "");
            Intent intent = new Intent();
            intent.putExtra("result", result);
            setResult(2, intent);
            finish();
        }
    }
}

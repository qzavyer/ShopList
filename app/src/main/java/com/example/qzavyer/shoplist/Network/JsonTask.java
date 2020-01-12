package com.example.qzavyer.shoplist.Network;

import android.os.AsyncTask;

import com.example.qzavyer.shoplist.Common.IUpdater;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class JsonTask extends AsyncTask<String, String, String> {
    private IUpdater updater;

    JsonTask(IUpdater updater){
        this.updater = updater;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(@NotNull String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
 if(result == null) result = "";
        this.updater.updateCurrency(result);
    }
}

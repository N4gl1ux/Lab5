package com.example.lab5;

import android.os.AsyncTask;

import java.io.IOException;

public class ThreadDataLoader extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... params) {

        try {

            return ApiDataReader.getValuesFromApi(params[0], params[1]);
        }
        catch (IOException ex) {

            return String.format("Some error occured => %s", ex.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
    }
}

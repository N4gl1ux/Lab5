package com.example.lab5;

import static com.example.lab5.Constants.FLOATRATES_API_URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiDataReader {
    public static String getValuesFromApi(String apiCode, String selectedCurrency) throws IOException {

        InputStream apiContentStream = null;
        String result = "";
        try {

            apiContentStream = downloadUrlContent(FLOATRATES_API_URL);
            result = FloatRatesParser.getCurrencyRates(apiContentStream, selectedCurrency);
        }
        finally {
            if (apiContentStream != null) {
                apiContentStream.close();
            }
        }
        return result;
    }

    private static InputStream downloadUrlContent(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}

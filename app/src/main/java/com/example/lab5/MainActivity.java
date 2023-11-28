package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private TextView tvStatus, tvGuide;
    private EditText edInput;
    private ArrayAdapter listAdapter;
    private Button btnGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvItems = findViewById(R.id.lv_items);
        this.tvStatus = findViewById(R.id.tv_status);
        this.tvGuide = findViewById(R.id.tv_guide);
        this.edInput = findViewById(R.id.ed_input);
        this.btnGetData = findViewById(R.id.btn_get_data);

        this.listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        this.lvItems.setAdapter(this.listAdapter);
    }

    public void onBtnGetDataClick(View view) {

        String selectedCurrency = edInput.getText().toString().trim();
        this.tvStatus.setText(R.string.loading_data);
        getDataByThread(selectedCurrency);
        Toast.makeText(this, R.string.msg_using_thread, Toast.LENGTH_LONG).show();
    }

    public void getDataByThread(String selectedCurrency) {

        this.tvStatus.setText(R.string.loading_data);
        Runnable getDataAndDisplayRunnable = new Runnable() {

            @Override
            public void run() {
                try {

                    final String result = ApiDataReader.getValuesFromApi(Constants.FLOATRATES_API_URL, selectedCurrency);
                    Runnable updateUIRunnable = new Runnable() {

                        @Override
                        public void run() {
                            tvStatus.setText(getString(R.string.data_loaded) + result);
                        }
                    };
                    runOnUiThread(updateUIRunnable);

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(getDataAndDisplayRunnable);
        thread.start();
    }
}
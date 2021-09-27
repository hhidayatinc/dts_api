package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TampilActivity extends AppCompatActivity {

    private TextView tvName, tvNoHp, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        tvName = findViewById(R.id.tv_nama);
        tvNoHp = findViewById(R.id.tv_noHp);
        tvEmail = findViewById(R.id.tv_email);

        Intent receivedData = getIntent();
        Bundle data = receivedData.getExtras();
        tvName.setText(data.getString("NAMA"));
        tvNoHp.setText(data.getString("NO TELEPON"));
        tvEmail.setText(data.getString("EMAIL"));
    }
}
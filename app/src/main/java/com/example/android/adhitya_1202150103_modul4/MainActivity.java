package com.example.android.adhitya_1202150103_modul4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button listMahasiswa;
    private Button PencariGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listMahasiswa = (Button)findViewById(R.id.buttonList);
        PencariGambar = (Button)findViewById(R.id.buttonGam);
    }

    public void listNama(View view) {
        Intent listNam = new Intent(MainActivity.this, listNamaMahasiswa.class);
        startActivity(listNam);
    }

    public void pencariGambar(View view) {
        Intent pencariGam = new Intent(MainActivity.this, pencariGambar.class);
        startActivity(pencariGam);
    }
}

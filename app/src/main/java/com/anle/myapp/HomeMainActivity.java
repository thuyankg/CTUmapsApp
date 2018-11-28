package com.anle.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
    }

    public void findMap(View view) {
        Intent intent = new Intent(this, MapMainActivity.class);
        startActivity(intent);
    }
    public void find3Dmodel(View view) {
        Intent intent = new Intent(this, Display3dmodelMainActivity.class);
        startActivity(intent);
    }
    public void huongDan(View view) {
        Intent intent = new Intent(this, NoteMainActivity.class);
        startActivity(intent);
    }




}

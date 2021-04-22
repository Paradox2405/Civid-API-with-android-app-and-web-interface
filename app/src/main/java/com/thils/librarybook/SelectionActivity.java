package com.thils.librarybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }

    public void onAdd(View v){
        startActivity(new Intent(SelectionActivity.this, AddActivity.class));
    }

    public void onDelete(View v){
        startActivity(new Intent(SelectionActivity.this, DeleteActivity.class));
    }
}

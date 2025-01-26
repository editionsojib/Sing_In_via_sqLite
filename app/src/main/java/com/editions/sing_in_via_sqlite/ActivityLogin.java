package com.editions.sing_in_via_sqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

        MyDBHelper myDBHelper;
        Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Database  initialization
        myDBHelper = new MyDBHelper(this);
        SQLiteDatabase sqLiteDatabase= myDBHelper.getWritableDatabase();


        button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);

        });


    }//OnCreate
}//Main
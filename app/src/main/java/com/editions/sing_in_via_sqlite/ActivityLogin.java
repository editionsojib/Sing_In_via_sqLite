package com.editions.sing_in_via_sqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;

public class ActivityLogin extends AppCompatActivity {

    MyDBHelper myDBHelper;
    EditText editTextEmail, editTextPassword;
    TextView textViewRegisterNow;
    CircularProgressButton circularProgressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ID  Finding from Layout
        circularProgressButton = findViewById(R.id.cirLoginButton);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewRegisterNow = findViewById(R.id.register_now);



        //myDBHelper new Object Created
        myDBHelper = new MyDBHelper(this);



        //circularProgressButton Login Button Call here for animated login Button
        circularProgressButton.setOnClickListener(v -> {
            //editTextEmail &editTextPassword Value Check
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                editTextEmail.setError("Enter Valid Email");
                editTextPassword.setError("Enter Valid Password");
            }else{
                //Start Animation for Login Button
                circularProgressButton.startAnimation();

                //this Thread Call Intent 2000 second after login button click
                new Handler().postDelayed(() -> {
                    //Call MyDBHelper & Find User From Database
                    if (myDBHelper !=null){
                        Boolean result = myDBHelper.findUser(email, password);
                        if (result)
                            startActivity(new Intent(ActivityLogin.this, HomeActivity.class));
                        else{
                            circularProgressButton.revertAnimation();
                            Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        circularProgressButton.revertAnimation();
                        Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
                    }
                },2000);




            }//if_else END
        });//END

        //textViewRegisterNow Call Here, User Have No Account Register Now
        textViewRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
        });







    }//OnCreate
}//Main
package com.example.stocktradingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Test Credentials
    private String keyid = "admin";
    private String keypass = "password";

    private EditText editTextID;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid;
                String pass;

                userid = editTextID.getText().toString();
                pass = editTextPassword.getText().toString();


                if((userid.equals(keyid)) && (pass.equals(keypass))){
                    Toast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_SHORT).show();
                    Intent startIntent = new Intent(getApplicationContext(),Navigation.class);
                    startActivity(startIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_SHORT).show();
                    editTextID.getText().clear();
                    editTextPassword.getText().clear();

                }
            }
        });

    }
}

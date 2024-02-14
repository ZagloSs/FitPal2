package com.example.fitpal20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText etCorreo, etPass;
    Button btnLogin;
    TextView tvRegister;
    String verCorreo, verPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.orange));

        etCorreo = findViewById(R.id.login_email_editText);
        etPass = findViewById(R.id.login_pass_editText);
        btnLogin = findViewById(R.id.login_login_btn);
        tvRegister = findViewById(R.id.login_sinRegistro_textView);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verCorreo = etCorreo.getText().toString();
                verPass = etPass.getText().toString();

                if(verCorreo.equals("")){
                    etCorreo.setError("Porfavor, introduzca un correo");
                    etCorreo.requestFocus();
                }else if(verPass.equals("")){
                    etPass.setError("Porfavor introduce una contraseña");
                    etPass.requestFocus();
                }else{

                    //Petición a la base de datos y entonces realizar el intent
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
}
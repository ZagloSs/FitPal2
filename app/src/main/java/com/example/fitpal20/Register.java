package com.example.fitpal20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitpal20.models.Usuario;
import com.example.fitpal20.retrofit.APIClient;
import com.example.fitpal20.retrofit.APIService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    Button btnRegister;
    EditText etCorreo, etPass, etConPass;
    TextView tvLogin;

    //Parametros para el toast
    CharSequence textToast;
    Integer duration;

    //If correo
    String verCorreo, verPass, verConPass;

    APIClient apiClient;
    APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.orange));

        btnRegister= findViewById(R.id.register_register_btn);
        etCorreo =  findViewById(R.id.register_email_editText);
        etPass = findViewById(R.id.register_pass_editText);
        etConPass = findViewById(R.id.register_confirmPass_editText);
        tvLogin = findViewById(R.id.register_login_textView);

        //Instancia de la api
        apiClient = APIClient.getInstance();
        apiClient.ApiClient();
        apiService = apiClient.getApiService();

        //Parametros para el toast
        textToast = "Te has registrado correctamente";
        duration = Toast.LENGTH_LONG;

        //Parametros para el if

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //If Verificacion si esta vacio
        MessageDigest finalMd = md;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                verCorreo = etCorreo.getText().toString();
                verPass = etPass.getText().toString();
                verConPass = etConPass.getText().toString();




                if(verCorreo.equals("")){
                    etCorreo.setError("Porfavor ingresa un correo electrónico");
                    etCorreo.requestFocus();


                }else if (verPass.equals("")){
                    etPass.setError("Porfavor introduzca una contraseña");
                    etPass.requestFocus();


                } else if(verConPass.equals("")){
                    etConPass.setError("Porfavor confirme una contraseña");
                    etConPass.requestFocus();

                } else if (!verConPass.equals(verPass)) {
                    etPass.setError("Las contraseñas no coinciden");
                    etConPass.setError("Las contraseñas no coinciden");
                    etConPass.requestFocus();
                }else if(!verCorreo.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                    etCorreo.setError("No es un formato valido de contraseña");
                    etCorreo.requestFocus();
                }else{


                    Usuario newuser = new Usuario("Prueba", 2.3f, 200f, "VayaEsperpento", 3, "Prueba", verCorreo, verPass);
                    registrarUsuario(apiService, newuser);



                    //finalMd.update(salt);
                    //byte[] hashedPasswordBytes = finalMd.digest(verPass.getBytes(StandardCharsets.UTF_8));
                   // String hashPass = new String(hashedPasswordBytes, StandardCharsets.UTF_8);


                   // Intent intent = new Intent(Register.this, Login.class);
                    //Toast toast = Toast.makeText(Register.this, textToast, duration);
                    //toast.show();

                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void registrarUsuario(APIService apiService, Usuario user){
        if(apiService != null){
            Call<Usuario> callRegUser = apiService.postUser(user);
            callRegUser.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        Usuario user = response.body();
                        Log.d("Response", user.getContraseña());
                        Toast.makeText(Register.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();


                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(Register.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
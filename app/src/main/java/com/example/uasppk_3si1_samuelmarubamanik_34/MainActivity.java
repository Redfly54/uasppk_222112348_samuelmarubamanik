package com.example.uasppk_3si1_samuelmarubamanik_34;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin,btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    loginUser(email, password);
                } else {
                    Toast.makeText(MainActivity.this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        // Asumsikan AuthService adalah interface Retrofit untuk autentikasi
        AuthService service = ApiClient.getClientForLogin(MainActivity.this).create(AuthService.class);
        Call<LoginResponse> call = service.login(new LoginRequest(email, password));

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginDebug", "Response Code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getAccessToken();
                    List<String> roles = response.body().getRoles();
                    Log.d("LoginDebug", "Token: " + token);
                    saveToken(token);
//                    saveRoles(roles);
                    Log.d("LoginDebug", "roles: " + roles);
                    navigateBasedOnRole(roles);
                } else {
                    String errorBody = null;
                    try {
                        errorBody = response.errorBody() != null ? response.errorBody().string() : null;
                    } catch (IOException e) {
                        Log.e("LoginDebug", "Error reading error body", e);
                    }
                    Log.d("LoginDebug", "Response Body: " + errorBody);
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToken(String token) {
        Log.d("LoginDebug", "Saving Token: " + token);
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwt_token", token);
        editor.apply();
    }

    private void saveRoles(List<String> roles) {
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String rolesString = TextUtils.join(",", roles);
        editor.putString("user_roles", rolesString);
        editor.apply();
    }

    private void navigateBasedOnRole(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            navigateToInputDaerahActivity();
        } else if (roles.contains("ROLE_USER")) {
            navigateToUserActivity();
        } else {
            Toast.makeText(MainActivity.this, "Role not recognized", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToInputDaerahActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Jika kamu ingin MainActivity tidak kembali lagi setelah InputDaerahActivity
    }

    private void navigateToUserActivity() {
        Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
        startActivity(intent);
        finish(); // Jika kamu ingin MainActivity tidak kembali lagi setelah InputDaerahActivity
    }
}
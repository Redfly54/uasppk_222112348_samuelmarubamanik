package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail;
    private Button btnChangePassword,btnHome,btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnHome = findViewById(R.id.btnHome);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, UserEditProfileActivity.class);
                startActivity(intent);
            }
        });

        loadUserProfile();
    }

    protected void onResume() {
        super.onResume();
        loadUserProfile(); // Memuat ulang data setiap kali kembali ke Activity
    }

    private void loadUserProfile() {
        AuthService service = ApiClient.getClient(this).create(AuthService.class);
        Call<UserProfile> call = service.getUserProfile();

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfile profile = response.body();
                    tvUserName.setText(profile.getName());
                    tvUserEmail.setText(profile.getEmail());
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Handle failure
            }
        });
    }
}

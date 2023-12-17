package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

    Button btnDaftarAnggota, btnDaftarKegiatan, btnDaftarDaerah,btnLogout,btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_home);

        // Inisialisasi Button
        btnDaftarAnggota = findViewById(R.id.btnDaftarAnggota);
        btnDaftarKegiatan = findViewById(R.id.btnDaftarKegiatan);
        btnDaftarDaerah = findViewById(R.id.btnDaftarDaerah);
        btnLogout = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);

        // Setup OnClickListener untuk setiap Button
        // Contoh:

        btnDaftarAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, UserDaftarAnggotaActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        btnDaftarKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, UserDaftarKegiatanActivity.class);
                startActivity(intent);
            }
        });

        btnDaftarDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, UserDaftarDaerahActivity.class);
                startActivity(intent);
            }
        });


        // Set listener untuk tombol lainnya
    }

    private void logout() {
        // Menghapus token JWT dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwt_token");
        editor.apply();

        // Tampilkan pesan logout berhasil
        Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();

        // Redirect ke MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // Tutup LogoutActivity
        finish();
    }

}

package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnDaftarAnggota, btnDaftarKegiatan, btnDaftarDaerah, btnTambahAnggota, btnTambahKegiatan, btnTambahDaerah, btnLogout,btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inisialisasi Button
        btnDaftarAnggota = findViewById(R.id.btnDaftarAnggota);
        btnDaftarKegiatan = findViewById(R.id.btnDaftarKegiatan);
        btnDaftarDaerah = findViewById(R.id.btnDaftarDaerah);
        btnTambahAnggota = findViewById(R.id.btnTambahAnggota);
        btnTambahKegiatan = findViewById(R.id.btnTambahKegiatan);
        btnTambahDaerah = findViewById(R.id.btnTambahDaerah);
        btnLogout = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        // Setup OnClickListener untuk setiap Button
        // Contoh:
        btnTambahAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TambahAnggotaActivity.class);
                startActivity(intent);
            }
        });

        btnDaftarAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DaftarAnggotaActivity.class);
                startActivity(intent);
            }
        });

        btnTambahKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TambahKegiatanActivity.class);
                startActivity(intent);
            }
        });

        btnDaftarKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DaftarKegiatanActivity.class);
                startActivity(intent);
            }
        });

        btnTambahDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InputDaerahActivity.class);
                startActivity(intent);
            }
        });

        btnDaftarDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DaftarDaerahActivity.class);
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
        Log.d("LoginDebug", "Performing Logout");

        // Tampilkan pesan logout berhasil
        Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();

        // Redirect ke MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // Tutup LogoutActivity
        finish();
    }
}

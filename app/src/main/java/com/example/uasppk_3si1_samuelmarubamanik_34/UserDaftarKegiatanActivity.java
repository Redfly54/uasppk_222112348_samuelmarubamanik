package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDaftarKegiatanActivity extends AppCompatActivity {

    private RecyclerView rvDaftarKegiatan;
    private UserKegiatanAdapter kegiatanAdapter;
    private List<Kegiatan> listKegiatan = new ArrayList<>();

    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_daftar_kegiatan);

        rvDaftarKegiatan = findViewById(R.id.rvDaftarKegiatan);
        kegiatanAdapter = new UserKegiatanAdapter(this,listKegiatan);
        rvDaftarKegiatan.setLayoutManager(new LinearLayoutManager(this));
        rvDaftarKegiatan.setAdapter(kegiatanAdapter);

        btnHome = findViewById(R.id.btnHome);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDaftarKegiatanActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

        Log.d("DaftarKegiatanActivity", "onCreate: Initializing DaftarKegiatanActivity");
        loadKegiatan();
    }

    protected void onResume() {
        super.onResume();
        loadKegiatan(); // Memuat ulang data setiap kali kembali ke Activity
    }

    private void loadKegiatan() {
        Log.d("DaftarKegiatanActivity", "loadKegiatan: Loading kegiatan data");
        KegiatanService kegiatanService = ApiClient.getClient(UserDaftarKegiatanActivity.this).create(KegiatanService.class);
        Call<List<Kegiatan>> call = kegiatanService.getAllKegiatan();

        call.enqueue(new Callback<List<Kegiatan>>() {
            @Override
            public void onResponse(Call<List<Kegiatan>> call, Response<List<Kegiatan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DaftarKegiatanActivity", "onResponse: Kegiatan data loaded");
                    listKegiatan.clear();
                    listKegiatan.addAll(response.body());
                    kegiatanAdapter.notifyDataSetChanged();

                    for(Kegiatan kegiatan : listKegiatan) {
                        Log.d("DaftarKegiatanActivity", "Kegiatan: " + kegiatan.getNamaKegiatan() + ", daerahId: " + kegiatan.getDaerahId());
                    }
                } else {
                    Log.e("DaftarKegiatanActivity", "onResponse: Failed to load kegiatan data");
                    Toast.makeText(UserDaftarKegiatanActivity.this, "Gagal memuat kegiatan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Kegiatan>> call, Throwable t) {
                Log.e("DaftarKegiatanActivity", "onFailure: Error loading kegiatan data", t);
                Toast.makeText(UserDaftarKegiatanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

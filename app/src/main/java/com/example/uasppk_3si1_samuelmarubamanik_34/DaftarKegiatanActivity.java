package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKegiatanActivity extends AppCompatActivity {
    private RecyclerView rvDaftarKegiatan;
    private KegiatanAdapter kegiatanAdapter;
    private List<Kegiatan> listKegiatan = new ArrayList<>();

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        rvDaftarKegiatan = findViewById(R.id.rvDaftarKegiatan);
        kegiatanAdapter = new KegiatanAdapter(listKegiatan,this);
        rvDaftarKegiatan.setLayoutManager(new LinearLayoutManager(this));
        rvDaftarKegiatan.setAdapter(kegiatanAdapter);

        home = findViewById(R.id.btnHome);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarKegiatanActivity.this,HomeActivity.class);
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
        KegiatanService kegiatanService = ApiClient.getClient(DaftarKegiatanActivity.this).create(KegiatanService.class);
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
                    Toast.makeText(DaftarKegiatanActivity.this, "Gagal memuat kegiatan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Kegiatan>> call, Throwable t) {
                Log.e("DaftarKegiatanActivity", "onFailure: Error loading kegiatan data", t);
                Toast.makeText(DaftarKegiatanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarDaerahActivity extends AppCompatActivity {
    private RecyclerView rvDaerah;
    private DaerahAdapter adapter;
    private List<Daerah> daerahList;

    private Button home;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_daerah);
        Log.d("DaftarDaerahActivity", "onCreate");

        rvDaerah = findViewById(R.id.rvDaerah);
        daerahList = new ArrayList<>();

        home = findViewById(R.id.btnHome);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarDaerahActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        adapter = new DaerahAdapter(this, daerahList);
        rvDaerah.setLayoutManager(new LinearLayoutManager(this));
        rvDaerah.setAdapter(adapter);

        ambilDataDaerah();
    }

    protected void onResume() {
        super.onResume();
        ambilDataDaerah(); // Memuat ulang data setiap kali kembali ke Activity
    }

    private void ambilDataDaerah() {
        Log.d("DaftarDaerahActivity", "Mengambil data daerah");
        DaerahService service = ApiClient.getClient(DaftarDaerahActivity.this).create(DaerahService.class);
        Call<List<Daerah>> call = service.getAllDaerah();

        call.enqueue(new Callback<List<Daerah>>() {
            @Override
            public void onResponse(Call<List<Daerah>> call, Response<List<Daerah>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DaftarDaerahActivity", "Data diterima: " + response.body().size() + " daerah");
                    daerahList.clear();
                    daerahList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("DaftarDaerahActivity", "Gagal menerima data: " + response.code());
                    Toast.makeText(DaftarDaerahActivity.this, "Gagal memuat data anggota", Toast.LENGTH_SHORT).show();
                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<List<Daerah>> call, Throwable t) {
                Log.e("DaftarDaerahActivity", "Error: " + t.getMessage());
                Toast.makeText(DaftarDaerahActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Handle failure
            }
        });
    }
}

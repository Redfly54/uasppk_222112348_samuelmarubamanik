package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.Bundle;
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

public class DaftarAnggotaActivity extends AppCompatActivity {

    private RecyclerView rvDaftarAnggota;
    private AnggotaAdapter anggotaAdapter;
    private List<Anggota> listAnggota = new ArrayList<>();

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);

        rvDaftarAnggota = findViewById(R.id.rvDaftarAnggota);
        anggotaAdapter = new AnggotaAdapter(this,listAnggota);
        rvDaftarAnggota.setLayoutManager(new LinearLayoutManager(this));
        rvDaftarAnggota.setAdapter(anggotaAdapter);

        home = findViewById(R.id.btnHome);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaftarAnggotaActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        // Inisialisasi AnggotaService dari ApiClient

        // Memuat data anggota
        loadDataAnggota();
    }

    protected void onResume() {
        super.onResume();
        loadDataAnggota(); // Memuat ulang data setiap kali kembali ke Activity
    }

    private void loadDataAnggota() {
        AnggotaService service = ApiClient.getClient(DaftarAnggotaActivity.this).create(AnggotaService.class);
        Call<List<Anggota>> call = service.getAllAnggota();

        call.enqueue(new Callback<List<Anggota>>() {
            @Override
            public void onResponse(Call<List<Anggota>> call, Response<List<Anggota>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listAnggota.clear();
                    listAnggota.addAll(response.body());
                    anggotaAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DaftarAnggotaActivity.this, "Gagal memuat data anggota", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Anggota>> call, Throwable t) {
                Toast.makeText(DaftarAnggotaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

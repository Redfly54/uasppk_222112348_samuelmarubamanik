package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKegiatanActivity extends AppCompatActivity {
    private Spinner spinnerDaerah;
    private EditText etNamaKegiatan, etDeskripsiKegiatan;
    private Button btnTambahKegiatan;
    private Map<String, Long> daerahMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        spinnerDaerah = findViewById(R.id.spinnerDaerah);
        etNamaKegiatan = findViewById(R.id.etNamaKegiatan);
        etDeskripsiKegiatan = findViewById(R.id.etDeskripsiKegiatan);
        btnTambahKegiatan = findViewById(R.id.btnTambahKegiatan);

        loadDaerah();

        btnTambahKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahKegiatan();
            }
        });
    }

    private void loadDaerah() {
        DaerahService daerahService = ApiClient.getClient(TambahKegiatanActivity.this).create(DaerahService.class);
        Call<List<Daerah>> call = daerahService.getAllDaerah();

        call.enqueue(new Callback<List<Daerah>>() {
            @Override
            public void onResponse(Call<List<Daerah>> call, Response<List<Daerah>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Daerah daerah : response.body()) {
                        daerahMap.put(daerah.getNamaDaerah(), daerah.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahKegiatanActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(daerahMap.keySet()));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDaerah.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Daerah>> call, Throwable t) {
                Toast.makeText(TambahKegiatanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahKegiatan() {
        String selectedDaerah = spinnerDaerah.getSelectedItem().toString();
        Long daerahId = daerahMap.get(selectedDaerah);
        String namaKegiatan = etNamaKegiatan.getText().toString();
        String deskripsi = etDeskripsiKegiatan.getText().toString();

        // Implementasi untuk menambah kegiatan
        Kegiatan newKegiatan = new Kegiatan();
        newKegiatan.setDaerahId(daerahId);
        newKegiatan.setNamaKegiatan(namaKegiatan);
        newKegiatan.setDeskripsiKegiatan(deskripsi);

        KegiatanService kegiatanService = ApiClient.getClient(TambahKegiatanActivity.this).create(KegiatanService.class);
        Call<Void> call = kegiatanService.tambahKegiatan(newKegiatan);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TambahKegiatanActivity.this, "Kegiatan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    // Opsional: Navigasi ke DaftarKegiatanActivity atau aktivitas lain
                    Intent intent = new Intent(TambahKegiatanActivity.this, DaftarKegiatanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TambahKegiatanActivity.this, "Gagal menambahkan kegiatan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TambahKegiatanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

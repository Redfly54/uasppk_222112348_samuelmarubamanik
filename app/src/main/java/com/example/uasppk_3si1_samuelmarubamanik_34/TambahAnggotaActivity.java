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

public class TambahAnggotaActivity extends AppCompatActivity {
    private Spinner spinnerDaerah;
    private EditText etFirstName, etLastName, etKelas;
    private Button btnTambah;
    private Map<String, Long> daerahMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);

        spinnerDaerah = findViewById(R.id.spinnerDaerah);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etKelas = findViewById(R.id.etKelas);
        btnTambah = findViewById(R.id.btnTambah);

        loadDaerah();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahAnggota();
            }
        });
    }

    private void loadDaerah() {
        DaerahService daerahService = ApiClient.getClient(TambahAnggotaActivity.this).create(DaerahService.class);
        Call<List<Daerah>> call = daerahService.getAllDaerah();

        call.enqueue(new Callback<List<Daerah>>() {
            @Override
            public void onResponse(Call<List<Daerah>> call, Response<List<Daerah>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Daerah daerah : response.body()) {
                        daerahMap.put(daerah.getNamaDaerah(), daerah.getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(TambahAnggotaActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(daerahMap.keySet()));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDaerah.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Daerah>> call, Throwable t) {
                Toast.makeText(TambahAnggotaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tambahAnggota() {
        String selectedDaerahName = spinnerDaerah.getSelectedItem().toString();
        Long selectedDaerahId = daerahMap.get(selectedDaerahName);
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String kelas = etKelas.getText().toString();

        Anggota newAnggota = new Anggota();
        newAnggota.setDaerahId(selectedDaerahId);
        newAnggota.setFirstName(firstName);
        newAnggota.setLastName(lastName);
        newAnggota.setKelas(kelas);

        AnggotaService anggotaService = ApiClient.getClient(TambahAnggotaActivity.this).create(AnggotaService.class);
        Call<Void> call = anggotaService.tambahAnggota(newAnggota);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TambahAnggotaActivity.this, "Anggota berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    // Opsional: Navigasi ke DaftarAnggotaActivity atau refresh data
                    Intent intent = new Intent(TambahAnggotaActivity.this, DaftarAnggotaActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(TambahAnggotaActivity.this, "Gagal menambahkan anggota", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TambahAnggotaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

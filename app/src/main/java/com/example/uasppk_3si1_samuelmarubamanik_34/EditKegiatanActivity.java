package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKegiatanActivity extends AppCompatActivity {

    private EditText editTextNamaKegiatan, editTextDeskripsiKegiatan;
    private Button buttonUpdateKegiatan;
    private Kegiatan kegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kegiatan);

        Log.d("EditKegiatanActivity", "Activity started");

        editTextNamaKegiatan = findViewById(R.id.editTextNamaKegiatan);
        editTextDeskripsiKegiatan = findViewById(R.id.editTextDeskripsiKegiatan);
        buttonUpdateKegiatan = findViewById(R.id.buttonUpdateKegiatan);

        kegiatan = (Kegiatan) getIntent().getSerializableExtra("KEGIATAN_DATA");
        if (kegiatan != null) {
            Log.d("EditKegiatanActivity", "Editing Kegiatan: " + kegiatan.getNamaKegiatan());
            editTextNamaKegiatan.setText(kegiatan.getNamaKegiatan());
            editTextDeskripsiKegiatan.setText(kegiatan.getDeskripsiKegiatan());
            Log.d("EditKegiatanActivity", "daerah id: " + kegiatan.getDaerahId());
        } else{
            Log.d("EditKegiatanActivity", "No Kegiatan data received");
        }

        buttonUpdateKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKegiatan();
            }
        });
    }

    private void updateKegiatan() {
        String nama = editTextNamaKegiatan.getText().toString();
        String deskripsi = editTextDeskripsiKegiatan.getText().toString();

        kegiatan.setNamaKegiatan(nama);
        kegiatan.setDeskripsiKegiatan(deskripsi);
        kegiatan.setDaerahId(kegiatan.getDaerahId());

        Log.d("UpdateKegiatan", "Kegiatan ID: " + kegiatan.getId() + ", Nama: " + nama + ", Deskripsi: " + deskripsi + ", DaerahID: " + kegiatan.getDaerahId());

        // API call untuk update kegiatan
        KegiatanService kegiatanService = ApiClient.getClient(this).create(KegiatanService.class);
        Call<Void> call = kegiatanService.updateKegiatan(kegiatan.getId(), kegiatan);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditKegiatanActivity.this, "Kegiatan berhasil diupdate", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke activity sebelumnya
                } else {
                    Toast.makeText(EditKegiatanActivity.this, "Update gagal: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditKegiatanActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

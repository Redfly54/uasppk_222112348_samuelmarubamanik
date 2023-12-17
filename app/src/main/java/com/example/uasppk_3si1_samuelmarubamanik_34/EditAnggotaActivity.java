package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAnggotaActivity extends AppCompatActivity {

    private EditText editTextNamaDepan, editTextNamaBelakang, editTextKelas;
    private Button buttonUpdateAnggota;
    private Anggota anggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anggota);

        editTextNamaDepan = findViewById(R.id.editTextNamaDepan);
        editTextNamaBelakang = findViewById(R.id.editTextNamaBelakang);
        editTextKelas = findViewById(R.id.editTextKelas);
        buttonUpdateAnggota = findViewById(R.id.buttonUpdateAnggota);

        anggota = (Anggota) getIntent().getSerializableExtra("ANGGOTA_DATA");
        if (anggota != null) {
            editTextNamaDepan.setText(anggota.getFirstName());
            editTextNamaBelakang.setText(anggota.getLastName());
            editTextKelas.setText(anggota.getKelas());
        }

        buttonUpdateAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnggota();
            }
        });
    }

    private void updateAnggota() {
        String namaDepan = editTextNamaDepan.getText().toString();
        String namaBelakang = editTextNamaBelakang.getText().toString();
        String kelas = editTextKelas.getText().toString();

        anggota.setFirstName(namaDepan);
        anggota.setLastName(namaBelakang);
        anggota.setKelas(kelas);
        anggota.setDaerahId(anggota.getDaerahId());

        AnggotaService anggotaService = ApiClient.getClient(this).create(AnggotaService.class);
        Call<Void> call = anggotaService.updateAnggota(anggota.getId(), anggota);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditAnggotaActivity.this, "Anggota berhasil diupdate", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditAnggotaActivity.this, "Update gagal: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditAnggotaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

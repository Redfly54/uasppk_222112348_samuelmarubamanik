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

public class EditDaerahActivity extends AppCompatActivity {
    private EditText editTextNamaDaerah, editTextDeskripsiDaerah;
    private Button buttonUpdateDaerah;
    private Daerah daerah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_daerah);

        editTextNamaDaerah = findViewById(R.id.editTextNamaDaerah);
        editTextDeskripsiDaerah = findViewById(R.id.editTextDeskripsiDaerah);
        buttonUpdateDaerah = findViewById(R.id.buttonUpdateDaerah);

        daerah = (Daerah) getIntent().getSerializableExtra("DAERAH_DATA");
        if (daerah != null) {
            editTextNamaDaerah.setText(daerah.getNamaDaerah());
            editTextDeskripsiDaerah.setText(daerah.getDeskripsi());
        }

        buttonUpdateDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDaerah();
            }
        });
    }

    private void updateDaerah() {
        String namaDaerah = editTextNamaDaerah.getText().toString();
        String deskripsiDaerah = editTextDeskripsiDaerah.getText().toString();
        daerah.setNamaDaerah(namaDaerah);
        daerah.setDeskripsi(deskripsiDaerah);

        // Panggil API untuk update daerah
        // Rest of the updateDaerah code
        DaerahService service = ApiClient.getClient(this).create(DaerahService.class);
        Call<Void> call = service.updateDaerah(daerah.getId(), daerah);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditDaerahActivity.this, "Daerah berhasil diupdate", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke activity sebelumnya
                } else {
                    Toast.makeText(EditDaerahActivity.this, "Update gagal: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditDaerahActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

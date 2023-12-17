package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputDaerahActivity extends AppCompatActivity {
    private EditText etNamaDaerah, etDeskripsiDaerah;
    private Button btnSimpan;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaDaerah = findViewById(R.id.etNamaDaerah);
        etDeskripsiDaerah = findViewById(R.id.etDeskripsiDaerah);
        btnSimpan = findViewById(R.id.btnSimpan);


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("InputDaerahActivity", "Tombol simpan diklik");
                String namaDaerah = etNamaDaerah.getText().toString();
                String deskripsi = etDeskripsiDaerah.getText().toString();

                // Validasi input
                if (validateInput(namaDaerah, deskripsi)) {
                    Daerah daerah = new Daerah(namaDaerah, deskripsi);
                    kirimDataKeServer(daerah);
                }
            }
        });
    }

    private boolean validateInput(String nama, String deskripsi) {
        // Validasi input (tidak kosong, dll)
        return !nama.isEmpty() && !deskripsi.isEmpty();
    }

    private void kirimDataKeServer(Daerah daerah) {
        Log.d("InputDaerahActivity", "service");
        DaerahService service = ApiClient.getClient(InputDaerahActivity.this).create(DaerahService.class);
        Call<Void> call = service.tambahDaerah(daerah);
        Log.d("InputDaerahActivity", "jaya");
//        Call<Void> call = null;
//        try {
//            Log.d("InputDaerahActivity", "service");
//            DaerahService service = ApiClient.getClient().create(DaerahService.class);
//            call = service.tambahDaerah(daerah);
//            Log.d("InputDaerahActivity", "jaya");
//        } catch (Exception e) {
//            Log.e("InputDaerahActivity", "Error: " + e.getMessage());
//        }
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("InputDaerahActivity", "successfull");
//                    Toast.makeText(InputDaerahActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InputDaerahActivity.this, DaftarDaerahActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure (misalnya error jaringan)
            }
        });
    }
}

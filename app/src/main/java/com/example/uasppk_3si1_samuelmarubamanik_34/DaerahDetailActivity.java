package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaerahDetailActivity extends AppCompatActivity {
    private TextView textViewNamaDaerah, textViewDeskripsi;
    // Tambahkan TextView atau View lain jika diperlukan untuk menampilkan data
    private RecyclerView recyclerViewKegiatan, recyclerViewAnggota;
    private KegiatanAdapter kegiatanAdapter;
    private AnggotaAdapter anggotaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daerah_detail);

        textViewNamaDaerah = findViewById(R.id.textViewNamaDaerah);
        textViewDeskripsi = findViewById(R.id.textViewDeskripsi);
        recyclerViewKegiatan = findViewById(R.id.recyclerViewKegiatan);
        recyclerViewAnggota = findViewById(R.id.recyclerViewAnggota);

        Long daerahId = getIntent().getLongExtra("DAERAH_ID", 0);
        if (daerahId != 0) {
            loadDaerahDetails(daerahId);
        }
    }

    private void loadDaerahDetails(Long daerahId) {
        DaerahService apiService = ApiClient.getClient(this).create(DaerahService.class);
        Call<Daerah> call = apiService.getDaerahById(daerahId);

        call.enqueue(new Callback<Daerah>() {
            @Override
            public void onResponse(Call<Daerah> call, Response<Daerah> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Daerah daerah = response.body();
                    textViewNamaDaerah.setText(daerah.getNamaDaerah());
                    textViewDeskripsi.setText(daerah.getDeskripsi());

                    kegiatanAdapter = new KegiatanAdapter(daerah.getKegiatanList());
                    recyclerViewKegiatan.setLayoutManager(new LinearLayoutManager(DaerahDetailActivity.this));
                    recyclerViewKegiatan.setAdapter(kegiatanAdapter);

                    anggotaAdapter = new AnggotaAdapter(daerah.getAnggotaList());
                    recyclerViewAnggota.setLayoutManager(new LinearLayoutManager(DaerahDetailActivity.this));
                    recyclerViewAnggota.setAdapter(anggotaAdapter);
                    // Set data lain jika diperlukan
                }
            }

            @Override
            public void onFailure(Call<Daerah> call, Throwable t) {
                // Tangani kegagalan
            }
        });
    }
}

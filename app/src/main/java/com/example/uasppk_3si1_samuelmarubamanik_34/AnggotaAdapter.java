package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.AnggotaViewHolder> {

    private List<Anggota> listAnggota;
    private Context context;

    public AnggotaAdapter(List<Anggota> listAnggota) {
        this.listAnggota = listAnggota;
    }

    public AnggotaAdapter(Context context, List<Anggota> listAnggota) {
        this.context = context;
        this.listAnggota = listAnggota;
    }

    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anggota, parent, false);
        return new AnggotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position) {
        Anggota anggota = listAnggota.get(position);
        holder.bind(anggota);
        holder.btnDeleteAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Anggota clickedAnggota = listAnggota.get(adapterPosition);
                    deleteAnggota(clickedAnggota.getId(), adapterPosition);
                }
            }
        });
        holder.btnEditAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Anggota clickedAnggota = listAnggota.get(adapterPosition);
                    Log.d("AnggotaAdapter", "Edit button clicked for: " + clickedAnggota.getFirstName() + " " + clickedAnggota.getLastName());
                    if (context != null){
                        Intent intent = new Intent(context, EditAnggotaActivity.class);
                        intent.putExtra("ANGGOTA_DATA", clickedAnggota);
                        context.startActivity(intent);
                    } else {
                        Log.e("AnggotaAdapter", "Context is null");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAnggota.size();
    }

    static class AnggotaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvKelas; // Tambahkan TextView lain jika diperlukan
        ImageView btnEditAnggota, btnDeleteAnggota;

        AnggotaViewHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaAnggota);
            tvKelas = itemView.findViewById(R.id.tvKelasAnggota);
            btnEditAnggota = itemView.findViewById(R.id.btnEditAnggota);
            btnDeleteAnggota = itemView.findViewById(R.id.btnDeleteAnggota);
        }

        void bind(Anggota anggota) {
            tvNama.setText(anggota.getFirstName() + " " + anggota.getLastName());
            tvKelas.setText(anggota.getKelas());
        }
    }

    private void deleteAnggota(Long id, int position) {
        AnggotaService service = ApiClient.getClient(context).create(AnggotaService.class);
        Call<Void> call = service.deleteAnggota(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Hapus item dari daerahList dan beri tahu adapter
                    listAnggota.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listAnggota.size());
                } else {
                    Log.e("DaerahAdapter", "Gagal menerima data: " + response.code());
                    // Handle error response
                    Toast.makeText(context, "Gagal menghapus daerah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

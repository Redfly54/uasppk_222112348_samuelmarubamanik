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

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.KegiatanViewHolder> {
    private List<Kegiatan> listKegiatan;

    private Context context;

    public KegiatanAdapter(List<Kegiatan> listKegiatan) {
        this.listKegiatan = listKegiatan;
    }

    public KegiatanAdapter(List<Kegiatan> listKegiatan, Context context) {
        this.listKegiatan = listKegiatan;
        this.context = context;
    }
    @NonNull
    @Override
    public KegiatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kegiatan, parent, false);
        return new KegiatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanViewHolder holder, int position) {
        Kegiatan kegiatan = listKegiatan.get(position);
        Log.d("KegiatanAdapter", "DaerahID: " + kegiatan.getDaerahId());
        holder.tvNamaKegiatan.setText(kegiatan.getNamaKegiatan());
        holder.tvDeskripsi.setText(kegiatan.getDeskripsiKegiatan());

        holder.btnEditKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Kegiatan clickedKegiatan = listKegiatan.get(adapterPosition);
                    Log.d("KegiatanAdapter", "Edit button clicked for: " + clickedKegiatan.getNamaKegiatan());
                    Log.d("KegiatanAdapter", "Edit button clicked for: " + clickedKegiatan.getDaerahId());
                    if (context != null){
                        Intent intent = new Intent(context, EditKegiatanActivity.class);
                        intent.putExtra("KEGIATAN_DATA", clickedKegiatan);
                        context.startActivity(intent);
                    }else{
                        Log.e("KegiatanAdapter","COntext is null");
                    }


                }
            }
        });

        holder.btnDeleteKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Kegiatan clickedAnggota = listKegiatan.get(adapterPosition);
                    deleteKegiatan(clickedAnggota.getId(), adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKegiatan.size();
    }

    public static class KegiatanViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKegiatan, tvDeskripsi;

        ImageView btnEditKegiatan, btnDeleteKegiatan;

        KegiatanViewHolder(View itemView) {
            super(itemView);
            tvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsiKegiatan);
            btnEditKegiatan = itemView.findViewById(R.id.btnEditKegiatan);
            btnDeleteKegiatan = itemView.findViewById(R.id.btnDeleteKegiatan);
        }
    }
    private void deleteKegiatan(Long id, int position) {
        KegiatanService service = ApiClient.getClient(context).create(KegiatanService.class);
        Call<Void> call = service.deleteKegiatan(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Hapus item dari daerahList dan beri tahu adapter
                    listKegiatan.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listKegiatan.size());
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

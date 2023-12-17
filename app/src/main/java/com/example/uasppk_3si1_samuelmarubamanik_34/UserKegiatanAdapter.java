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

public class UserKegiatanAdapter extends RecyclerView.Adapter<UserKegiatanAdapter.KegiatanViewHolder> {

    private List<Kegiatan> kegiatanList;
    private Context context;

    public UserKegiatanAdapter(List<Kegiatan> kegiatanList) {
        this.kegiatanList = kegiatanList;
    }

    public UserKegiatanAdapter(Context context, List<Kegiatan> kegiatanList) {
        this.context = context;
        this.kegiatanList = kegiatanList;
    }

    @NonNull
    @Override
    public KegiatanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_kegiatan, parent, false);
        return new KegiatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KegiatanViewHolder holder, int position) {
        Kegiatan kegiatan = kegiatanList.get(position);
        holder.tvNamaKegiatan.setText(kegiatan.getNamaKegiatan());
        holder.tvDeskripsi.setText(kegiatan.getDeskripsiKegiatan());
        // Anda bisa menambahkan lebih banyak data kegiatan di sini jika diperlukan
    }

    @Override
    public int getItemCount() {
        return kegiatanList.size();
    }

    public static class KegiatanViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKegiatan,tvDeskripsi;
        // Anda bisa menambahkan lebih banyak TextView atau ImageView sesuai dengan layout Anda

        public KegiatanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsiKegiatan);
            // Inisialisasi view lain jika ada
        }
    }
}

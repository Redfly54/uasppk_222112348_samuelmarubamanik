package com.example.uasppk_3si1_samuelmarubamanik_34;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAnggotaAdapter extends RecyclerView.Adapter<UserAnggotaAdapter.AnggotaViewHolder> {

    private List<Anggota> anggotaList;
    private Context context;

    public UserAnggotaAdapter(List<Anggota> anggotaList) {
        this.anggotaList = anggotaList;
    }

    public UserAnggotaAdapter(Context context, List<Anggota> anggotaList) {
        this.context = context;
        this.anggotaList = anggotaList;
    }

    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_anggota, parent, false);
        return new AnggotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position) {
        Anggota anggota = anggotaList.get(position);
//        holder.tvNamaAnggota.setText(anggota.getNamaAnggota());
//        holder.tvDetailAnggota.setText(anggota.getDetailAnggota());
        // Anda bisa menambahkan lebih banyak data anggota di sini jika diperlukan
    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }

    public static class AnggotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaAnggota, tvKelas;
        // Anda bisa menambahkan lebih banyak TextView atau ImageView sesuai dengan layout Anda

        public AnggotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaAnggota = itemView.findViewById(R.id.tvNamaAnggota);
            tvKelas = itemView.findViewById(R.id.tvKelasAnggota);
            // Inisialisasi view lain jika ada
        }
    }
}

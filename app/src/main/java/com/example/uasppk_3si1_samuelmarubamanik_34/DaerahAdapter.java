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

public class DaerahAdapter extends RecyclerView.Adapter<DaerahAdapter.DaerahViewHolder> {

    private List<Daerah> daerahList;
    private Context context;


    public DaerahAdapter(List<Daerah> daerahList) {
        this.daerahList = daerahList;
    }

    public DaerahAdapter(Context context, List<Daerah> daerahList) {
        this.context = context;
        this.daerahList = daerahList;
    }

    @NonNull
    @Override
    public DaerahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daerah, parent, false);
        return new DaerahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaerahViewHolder holder, int position) {
        Daerah daerah = daerahList.get(position);
        holder.tvNamaDaerah.setText(daerah.getNamaDaerah());
        Log.d("DaerahAdapter", "onBindViewHolder: posisi " + position + ", Nama: " + daerah.getNamaDaerah());

        holder.btnEditDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Daerah currentDaerah = daerahList.get(currentPosition);
                    Intent intent = new Intent(context, EditDaerahActivity.class);
                    intent.putExtra("DAERAH_DATA", currentDaerah);
                    context.startActivity(intent);
                }
            }
        });
        holder.btnDeleteDaerah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Daerah clickedDaerah = daerahList.get(adapterPosition);
                    deleteDaerah(clickedDaerah.getId(), adapterPosition);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Daerah currentDaerah = daerahList.get(currentPosition);
                    Intent intent = new Intent(context, DaerahDetailActivity.class);
                    intent.putExtra("DAERAH_ID", currentDaerah.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("DaerahAdapter", "getItemCount: " + daerahList.size());
        return daerahList.size();
    }

    public static class DaerahViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaDaerah;
        ImageView btnEditDaerah, btnDeleteDaerah;

        public DaerahViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaDaerah = itemView.findViewById(R.id.tvNamaDaerah);

                btnEditDaerah = itemView.findViewById(R.id.btnEditDaerah);
                btnDeleteDaerah = itemView.findViewById(R.id.btnDeleteDaerah);

        }
    }
    private void deleteDaerah(Long id, int position) {
        DaerahService service = ApiClient.getClient(context).create(DaerahService.class);
        Call<Void> call = service.deleteDaerah(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Hapus item dari daerahList dan beri tahu adapter
                    daerahList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, daerahList.size());
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

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

public class UserDaerahAdapter extends RecyclerView.Adapter<UserDaerahAdapter.DaerahViewHolder> {

    private List<Daerah> daerahList;
    private Context context;

    public UserDaerahAdapter(Context context, List<Daerah> daerahList) {
        this.context = context;
        this.daerahList = daerahList;
    }

    @NonNull
    @Override
    public DaerahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_daerah, parent, false);
        return new DaerahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaerahViewHolder holder, int position) {
        Daerah daerah = daerahList.get(position);
        holder.tvNamaDaerah.setText(daerah.getNamaDaerah());
        Log.d("UserDaerahAdapter", "onBindViewHolder: posisi " + position + ", Nama: " + daerah.getNamaDaerah());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Daerah currentDaerah = daerahList.get(currentPosition);
                    Intent intent = new Intent(context, UserDaerahDetailActivity.class);
                    intent.putExtra("DAERAH_ID", currentDaerah.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("UserDaerahAdapter", "getItemCount: " + daerahList.size());
        return daerahList.size();
    }

    public static class DaerahViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaDaerah;

        public DaerahViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaDaerah = itemView.findViewById(R.id.tvNamaDaerah);
        }
    }
}

package com.example.app_music.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_music.Model.FragmentHomeListMusicV1_Model;
import com.example.app_music.R;

import java.util.List;

public class FragmentHomeListMusicV1_Adapter extends RecyclerView.Adapter<FragmentHomeListMusicV1_Adapter.TopMusicViewHolder> {

    private List<FragmentHomeListMusicV1_Model> list;

    public FragmentHomeListMusicV1_Adapter(List<FragmentHomeListMusicV1_Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TopMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_list_music_v1, parent, false);
        return new TopMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMusicViewHolder holder, int position) {
        FragmentHomeListMusicV1_Model item = list.get(position);
        holder.imgBanner.setImageResource(item.getImageResId());
        holder.tvTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TopMusicViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        TextView tvTitle;

        public TopMusicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.img_item_top_music_banner);
            tvTitle = itemView.findViewById(R.id.tv_item_top_music_title);
        }
    }
}

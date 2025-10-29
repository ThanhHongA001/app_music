package com.example.app_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_music.Model.FragmentHomeListMusicV2_Model;
import com.example.app_music.R;

import java.util.List;

public class FragmentHomeListMusicV2_Adapter extends RecyclerView.Adapter<FragmentHomeListMusicV2_Adapter.ViewHolder> {

    private Context context;
    private List<FragmentHomeListMusicV2_Model> list;

    public FragmentHomeListMusicV2_Adapter(Context context, List<FragmentHomeListMusicV2_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list_music_v2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FragmentHomeListMusicV2_Model item = list.get(position);
        holder.tvTenBaiHat.setText(item.getTenBaiHat());
        holder.tvLuotNghe.setText(item.getSoLuotNghe() + " lượt nghe");
        holder.tvLuotThich.setText(item.getSoLuotThich() + " thích");
        holder.imgBia.setImageResource(item.getAnhBia());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBia, imgMore, imgViewIcon, imgLikeIcon;
        TextView tvTenBaiHat, tvLuotNghe, tvLuotThich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBia = itemView.findViewById(R.id.img_item_music_v2_cover);
            imgMore = itemView.findViewById(R.id.img_item_music_v2_more);
            imgViewIcon = itemView.findViewById(R.id.img_item_music_v2_view_icon);
            imgLikeIcon = itemView.findViewById(R.id.img_item_music_v2_like_icon);
            tvTenBaiHat = itemView.findViewById(R.id.tv_item_music_v2_name);
            tvLuotNghe = itemView.findViewById(R.id.tv_item_music_v2_view_count);
            tvLuotThich = itemView.findViewById(R.id.tv_item_music_v2_like_count);
        }
    }
}

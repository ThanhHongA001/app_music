package com.example.app_music.Adapter;

import android.content.Context;
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

public class FragmentHomeListMusicV1_Adapter extends RecyclerView.Adapter<FragmentHomeListMusicV1_Adapter.AlbumViewHolder> {

    private Context context;
    private List<FragmentHomeListMusicV1_Model> albumList;

    public FragmentHomeListMusicV1_Adapter(Context context, List<FragmentHomeListMusicV1_Model> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list_music_v1, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        FragmentHomeListMusicV1_Model album = albumList.get(position);
        holder.tvAlbumName.setText(album.getAlbumName());
        holder.imgAlbumCover.setImageResource(album.getAlbumImageResId());
    }

    @Override
    public int getItemCount() {
        return albumList != null ? albumList.size() : 0;
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlbumCover;
        TextView tvAlbumName;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbumCover = itemView.findViewById(R.id.img_item_album_v1_cover);
            tvAlbumName = itemView.findViewById(R.id.tv_item_album_v1_name);
        }
    }
}

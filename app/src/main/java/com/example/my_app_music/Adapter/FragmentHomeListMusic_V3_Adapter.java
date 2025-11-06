package com.example.my_app_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.List;

public class FragmentHomeListMusic_V3_Adapter extends RecyclerView.Adapter<FragmentHomeListMusic_V3_Adapter.ViewHolder> {

    private Context context;
    private List<Song> albumList;

    public FragmentHomeListMusic_V3_Adapter(Context context, List<Song> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list_music_v3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song album = albumList.get(position);

        holder.txtName.setText(album.album_name);

        // Load ảnh album bằng Glide
        Glide.with(context)
                .load(album.album_avatar_url != null ? album.album_avatar_url : R.drawable.bg_album_rounded)
                .into(holder.imgAlbum);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAlbum;
        TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.home_list_music_v3_img_album);
            txtName = itemView.findViewById(R.id.home_list_music_v3_txt_name);
        }
    }
}

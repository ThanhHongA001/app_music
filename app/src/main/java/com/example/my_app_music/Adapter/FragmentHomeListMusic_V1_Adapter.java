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

public class FragmentHomeListMusic_V1_Adapter extends RecyclerView.Adapter<FragmentHomeListMusic_V1_Adapter.ViewHolder> {

    private final Context context;
    private final List<Song> songs;
    private OnItemClickListener listener;

    // Interface xử lý khi người dùng click vào item
    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FragmentHomeListMusic_V1_Adapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list_music_v1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songs.get(position);

        holder.tvName.setText(song.title);
        holder.tvArtist.setText(song.artist_name);

        // Lấy ảnh đại diện bài hát (ưu tiên song_avatar_url)
        String imageUrl = song.song_avatar_url != null && !song.song_avatar_url.isEmpty()
                ? song.song_avatar_url
                : song.album_avatar_url;

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.bg_album_rounded)
                .error(R.drawable.bg_album_rounded)
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(song);
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_home_list_music_v1_imgSong);
            tvName = itemView.findViewById(R.id.item_home_list_music_v1_SongName);
            tvArtist = itemView.findViewById(R.id.item_home_list_music_v1_Artist);
        }
    }
}

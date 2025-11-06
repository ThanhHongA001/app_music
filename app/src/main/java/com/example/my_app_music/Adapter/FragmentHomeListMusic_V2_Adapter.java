package com.example.my_app_music.Adapter;

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

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeListMusic_V2_Adapter extends RecyclerView.Adapter<FragmentHomeListMusic_V2_Adapter.ViewHolder> {

    private List<Song> songs = new ArrayList<>();

    // Cập nhật dữ liệu bài hát, chỉ lấy tối đa 5 bài
    public void setSongs(List<Song> newSongs) {
        songs.clear();
        if (newSongs != null && !newSongs.isEmpty()) {
            int size = Math.min(newSongs.size(), 5);
            songs.addAll(newSongs.subList(0, size));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_list_music_v2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(songs.get(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSong, iconView, iconLike, btnMore;
        private TextView txtTitle, txtViews, txtLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSong = itemView.findViewById(R.id.home_list_music_v2_img_song);
            iconView = itemView.findViewById(R.id.home_list_music_v2_icon_view);
            iconLike = itemView.findViewById(R.id.home_list_music_v2_icon_like);
            btnMore = itemView.findViewById(R.id.home_list_music_v2_btn_more);
            txtTitle = itemView.findViewById(R.id.home_list_music_v2_txt_title);
            txtViews = itemView.findViewById(R.id.home_list_music_v2_txt_views);
            txtLikes = itemView.findViewById(R.id.home_list_music_v2_txt_likes);
        }

        public void bind(Song song) {
            txtTitle.setText(song.title);
            txtViews.setText("0 lượt nghe"); // placeholder
            txtLikes.setText("0 thích");     // placeholder
            Glide.with(itemView.getContext())
                    .load(song.song_avatar_url)
                    .placeholder(R.drawable.bg_album_rounded)
                    .into(imgSong);
        }
    }
}

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

public class ActivityListMusic_Adapter extends RecyclerView.Adapter<ActivityListMusic_Adapter.ViewHolder> {

    // ========== Data & Listener ==========

    private final List<Song> songs = new ArrayList<>();

    public interface OnSongClickListener {
        void onSongClick(Song song);
    }

    private OnSongClickListener onSongClickListener;

    // Setter listener click item
    public void setOnSongClickListener(OnSongClickListener listener) {
        this.onSongClickListener = listener;
    }

    // Cập nhật dữ liệu cho adapter
    public void setData(List<Song> data) {
        songs.clear();
        if (data != null) {
            songs.addAll(data);
        }
        notifyDataSetChanged();
    }

    // ========== Override RecyclerView.Adapter ==========

    @NonNull
    @Override
    public ActivityListMusic_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListMusic_Adapter.ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song, onSongClickListener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    // ========== Nhóm hàm hỗ trợ tạo View ==========

    private View inflateItemView(ViewGroup parent) {
        // Tái sử dụng layout item_home_list_music_v2
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_list_music_v2, parent, false);
    }

    // ========== ViewHolder ==========

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgSong, iconView, iconLike, btnMore;
        private final TextView txtTitle, txtViews, txtLikes;

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

        // ===== Nhóm hàm public để bind =====

        public void bind(Song song, OnSongClickListener listener) {
            bindTitle(song);
            bindStats(song);
            bindImage(song);
            setupClickListeners(song, listener);
        }

        // ===== Nhóm hàm bind dữ liệu =====

        private void bindTitle(Song song) {
            txtTitle.setText(song.title);
        }

        private void bindStats(Song song) {
            // Nếu sau này có field views / likes thì sửa chỗ này thôi
            txtViews.setText("0 lượt nghe");
            txtLikes.setText("0 thích");
        }

        private void bindImage(Song song) {
            Glide.with(itemView.getContext())
                    .load(song.song_avatar_url)
                    .placeholder(R.drawable.bg_album_rounded)
                    .into(imgSong);
        }

        // ===== Nhóm hàm xử lý sự kiện click =====

        private void setupClickListeners(Song song, OnSongClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSongClick(song);
                }
            });

            btnMore.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSongClick(song);
                }
            });
        }
    }
}

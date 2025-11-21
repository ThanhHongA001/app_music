package com.example.my_app_music.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.ArrayList;
import java.util.List;

// Adapter cho ListView kết quả tìm kiếm
public class FragmentSearch_Adapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<Song> songs;

    public FragmentSearch_Adapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.songs = new ArrayList<>();
    }

    // Cập nhật dữ liệu search
    public void setData(List<Song> newSongs) {
        songs.clear();
        if (newSongs != null) {
            songs.addAll(newSongs);
        }
        notifyDataSetChanged();
    }

    public Song getItemSong(int position) {
        if (position < 0 || position >= songs.size()) return null;
        return songs.get(position);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return songs.get(position).id;
    }

    static class ViewHolder {
        ImageView imgAlbum;
        TextView txtName;
        ImageView btnMore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.imgAlbum = convertView.findViewById(R.id.search_img_album);
            holder.txtName = convertView.findViewById(R.id.search_txt_name);
            holder.btnMore = convertView.findViewById(R.id.search_btn_more);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song song = songs.get(position);

        // ====== Hiển thị tên: "title - artist" ======
        String title = song.title != null ? song.title : "";
        String artist = song.artist_name != null ? song.artist_name : "";

        String displayName;
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(artist)) {
            displayName = title + " - " + artist;
        } else if (!TextUtils.isEmpty(title)) {
            displayName = title;
        } else if (!TextUtils.isEmpty(artist)) {
            displayName = artist;
        } else {
            displayName = "Unknown";
        }
        holder.txtName.setText(displayName);

        // ====== Hiển thị ảnh thật từ API ======
        // Ưu tiên: song_avatar_url -> album_avatar_url -> placeholder
        String imageUrl = null;
        if (!TextUtils.isEmpty(song.song_avatar_url)) {
            imageUrl = song.song_avatar_url;
        } else if (!TextUtils.isEmpty(song.album_avatar_url)) {
            imageUrl = song.album_avatar_url;
        }

        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.bg_album_rounded)   // đang load
                    .error(R.drawable.bg_album_rounded)         // lỗi / URL hỏng
                    .centerCrop()
                    .into(holder.imgAlbum);
        } else {
            // Không có URL -> dùng placeholder
            holder.imgAlbum.setImageResource(R.drawable.bg_album_rounded);
        }

        holder.btnMore.setOnClickListener(v -> {
            // TODO: xử lý menu more (popup, bottom sheet, v.v.) nếu cần
        });

        return convertView;
    }
}

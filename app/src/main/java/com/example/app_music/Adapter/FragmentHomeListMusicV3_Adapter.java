package com.example.app_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_music.Model.FragmentHomeListMusicV3_Model;
import com.example.app_music.R;

import java.util.List;

/**
 * 🎵 Adapter hiển thị danh sách album (thể loại)
 */
public class FragmentHomeListMusicV3_Adapter extends RecyclerView.Adapter<FragmentHomeListMusicV3_Adapter.AlbumViewHolder> {

    private final Context context;
    private final List<FragmentHomeListMusicV3_Model> albumList;

    public FragmentHomeListMusicV3_Adapter(Context context, List<FragmentHomeListMusicV3_Model> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_list_music_v3, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        FragmentHomeListMusicV3_Model album = albumList.get(position);
        holder.tvAlbumName.setText(album.getAlbumName());
        holder.imgAlbumCover.setImageResource(album.getAlbumImageResId());

        // 📀 Sự kiện khi click vào album
        holder.itemView.setOnClickListener(v -> onAlbumClick(album));
    }

    @Override
    public int getItemCount() {
        return albumList != null ? albumList.size() : 0;
    }

    // ============================== ⚙️ HÀM XỬ LÝ SỰ KIỆN ==============================

    private void onAlbumClick(FragmentHomeListMusicV3_Model album) {
        // 👉 TODO: Mở Fragment hiển thị danh sách bài hát theo thể loại
        // Có thể gửi dữ liệu qua Bundle nếu cần
    }

    // ============================== ⚙️ VIEW HOLDER ==============================

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

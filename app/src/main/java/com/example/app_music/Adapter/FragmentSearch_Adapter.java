package com.example.app_music.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_music.Model.FragmentSearch_Model;
import com.example.app_music.R;

import java.util.List;

public class FragmentSearch_Adapter extends BaseAdapter {
    private Context context;
    private List<FragmentSearch_Model> dataList;

    public FragmentSearch_Adapter(Context context, List<FragmentSearch_Model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView imgAlbum;
        TextView tvTitle;
        ImageView imgMore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
            holder = new ViewHolder();
            holder.imgAlbum = convertView.findViewById(R.id.img_item_search_album);
            holder.tvTitle = convertView.findViewById(R.id.tv_item_search_title);
            holder.imgMore = convertView.findViewById(R.id.img_item_search_more);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FragmentSearch_Model item = dataList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.imgAlbum.setImageResource(item.getImageResId());

        return convertView;
    }

    public void updateData(List<FragmentSearch_Model> newList) {
        this.dataList = newList;
        notifyDataSetChanged();
    }
}

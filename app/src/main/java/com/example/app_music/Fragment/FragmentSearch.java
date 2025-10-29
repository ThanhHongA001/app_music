package com.example.app_music.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app_music.Adapter.FragmentSearch_Adapter;
import com.example.app_music.Model.FragmentSearch_Model;
import com.example.app_music.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FragmentSearch extends Fragment {

    private EditText edtSearch;
    private ImageButton btnClear;
    private ListView listResult;
    private FragmentSearch_Adapter adapter;

    private List<FragmentSearch_Model> fullList;  // dữ liệu gốc
    private List<FragmentSearch_Model> filteredList; // dữ liệu sau khi lọc

    public FragmentSearch() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Ánh xạ view
        edtSearch = view.findViewById(R.id.edt_search_input);
        btnClear = view.findViewById(R.id.btn_search_clear);
        listResult = view.findViewById(R.id.list_search_result);

        // Tạo danh sách mẫu (có thể sau này load từ DB hoặc API)
        fullList = new ArrayList<>();
        fullList.add(new FragmentSearch_Model("Em Của Ngày Hôm Qua", R.drawable.bg_album_rounded));
        fullList.add(new FragmentSearch_Model("Hãy Trao Cho Anh", R.drawable.bg_album_rounded));
        fullList.add(new FragmentSearch_Model("Lạc Trôi", R.drawable.bg_album_rounded));
        fullList.add(new FragmentSearch_Model("Có Chắc Yêu Là Đây", R.drawable.bg_album_rounded));
        fullList.add(new FragmentSearch_Model("Đôi Mươi", R.drawable.bg_album_rounded));

        filteredList = new ArrayList<>(fullList);

        // Khởi tạo adapter
        adapter = new FragmentSearch_Adapter(requireContext(), filteredList);
        listResult.setAdapter(adapter);

        // Tìm kiếm theo từ khóa
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim().toLowerCase();

                btnClear.setVisibility(keyword.isEmpty() ? View.GONE : View.VISIBLE);

                if (keyword.isEmpty()) {
                    filteredList = new ArrayList<>(fullList);
                } else {
                    filteredList = fullList.stream()
                            .filter(item -> item.getTitle().toLowerCase().contains(keyword))
                            .collect(Collectors.toList());
                }
                adapter.updateData(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Nút xóa text
        btnClear.setOnClickListener(v -> edtSearch.setText(""));

        // Xử lý click item
        listResult.setOnItemClickListener((parent, view1, position, id) -> {
            FragmentSearch_Model selected = filteredList.get(position);
            Toast.makeText(requireContext(), "Đã chọn: " + selected.getTitle(), Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}

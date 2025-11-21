package com.example.my_app_music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my_app_music.Activity.Music.ActivityPlayMusic;
import com.example.my_app_music.Adapter.FragmentSearch_Adapter;
import com.example.my_app_music.R;
import com.example.my_app_music.Utils_Api.Api.Songs.ApiClient_Music;
import com.example.my_app_music.Utils_Api.Api.Songs.ApiService_Music;
import com.example.my_app_music.Utils_Api.Api.Songs.Constants_Music;
import com.example.my_app_music.Utils_Api.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {

    private EditText edtSearch;
    private ImageButton btnClear;
    private ListView listResults;

    private FragmentSearch_Adapter searchAdapter;
    private ApiService_Music apiServiceMusic;

    // Debounce
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private Call<List<Song>> currentCall;

    public FragmentSearch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // KHÔNG sửa XML
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapViews(view);
        setupApi();
        setupListView();
        setupSearchBox();
    }

    // ===================== INIT =====================

    private void mapViews(View view) {
        edtSearch = view.findViewById(R.id.search_input);
        btnClear = view.findViewById(R.id.search_btn_clear);
        listResults = view.findViewById(R.id.search_list_results);
    }

    private void setupApi() {
        apiServiceMusic = ApiClient_Music.getClient().create(ApiService_Music.class);
    }

    private void setupListView() {
        searchAdapter = new FragmentSearch_Adapter(requireContext());
        listResults.setAdapter(searchAdapter);

        // Click 1 bài hát trong list -> mở màn PlayMusic
        listResults.setOnItemClickListener((parent, view, position, id) -> {
            Song song = searchAdapter.getItemSong(position);
            if (song == null) return;

            Intent intent = new Intent(requireContext(), ActivityPlayMusic.class);
            intent.putExtra("song_id", song.id);
            intent.putExtra("song_title", song.title);
            intent.putExtra("song_artist", song.artist_name);

            // ====== QUAN TRỌNG: dùng key TRÙNG với ActivityPlayMusic + giữ cả key cũ ======
            // Key mà ActivityPlayMusic đang đọc:
            intent.putExtra("song_url", song.mp3_url);
            intent.putExtra("song_avatar", song.song_avatar_url);

            // Giữ thêm key cũ (nếu sau này bạn dùng ở đâu đó):
            intent.putExtra("song_mp3_url", song.mp3_url);
            intent.putExtra("song_avatar_url", song.song_avatar_url);

            startActivity(intent);
        });
    }

    private void setupSearchBox() {
        // Nút clear
        btnClear.setOnClickListener(v -> {
            edtSearch.setText("");
            searchAdapter.setData(null);
            btnClear.setVisibility(View.GONE);
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();

                // Show / hide nút clear
                btnClear.setVisibility(keyword.isEmpty() ? View.GONE : View.VISIBLE);

                // Huỷ runnable cũ (debounce)
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                // Nếu chuỗi trống hoặc quá ngắn => xoá list & không call API
                if (keyword.length() < 2) {
                    searchAdapter.setData(null);
                    return;
                }

                // Debounce 400ms
                searchRunnable = () -> callSearchApi(keyword);
                handler.postDelayed(searchRunnable, 400);
            }
        });
    }

    // ===================== CALL API =====================

    private void callSearchApi(String keyword) {
        // Huỷ call cũ nếu còn chạy
        if (currentCall != null && !currentCall.isCanceled()) {
            currentCall.cancel();
        }

        // Tạo URL động từ Constants (KHÔNG sửa Constants)
        String url = Constants_Music.buildSearchSongsUrl(keyword);

        // Gọi API bằng Retrofit
        currentCall = apiServiceMusic.getSongsByDynamicUrl(url);

        currentCall.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call,
                                   @NonNull Response<List<Song>> response) {
                if (!isAdded()) return; // fragment đã bị detach

                if (response.isSuccessful()) {
                    List<Song> songs = response.body();

                    // Nếu body null hoặc rỗng => thông báo
                    if (songs == null || songs.isEmpty()) {
                        searchAdapter.setData(null);
                        Toast.makeText(requireContext(),
                                "Không tìm thấy bài hát phù hợp",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        searchAdapter.setData(songs);
                    }
                } else {
                    searchAdapter.setData(null);
                    Toast.makeText(requireContext(),
                            "Lỗi server: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call,
                                  @NonNull Throwable t) {
                if (!isAdded()) return;
                if (call.isCanceled()) {
                    // Bị cancel do user gõ tiếp -> bỏ qua
                    return;
                }
                searchAdapter.setData(null);
                Toast.makeText(requireContext(),
                        "Không thể kết nối server\n" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (currentCall != null) currentCall.cancel();
        handler.removeCallbacksAndMessages(null);
    }
}

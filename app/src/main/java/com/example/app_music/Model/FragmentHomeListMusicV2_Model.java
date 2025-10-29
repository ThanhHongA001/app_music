package com.example.app_music.Model;

public class FragmentHomeListMusicV2_Model {
    private String tenBaiHat;
    private int soLuotNghe;
    private int soLuotThich;
    private int anhBia;

    public FragmentHomeListMusicV2_Model(String tenBaiHat, int soLuotNghe, int soLuotThich, int anhBia) {
        this.tenBaiHat = tenBaiHat;
        this.soLuotNghe = soLuotNghe;
        this.soLuotThich = soLuotThich;
        this.anhBia = anhBia;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public int getSoLuotNghe() {
        return soLuotNghe;
    }

    public int getSoLuotThich() {
        return soLuotThich;
    }

    public int getAnhBia() {
        return anhBia;
    }
}

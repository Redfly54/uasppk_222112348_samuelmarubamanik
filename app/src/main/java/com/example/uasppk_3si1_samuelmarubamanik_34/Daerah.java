package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.io.Serializable;
import java.util.List;

public class Daerah implements Serializable {
    private Long id;
    private String namaDaerah;
    private String deskripsi;

    private List<Kegiatan> kegiatanList;
    private List<Anggota> anggotaList;

    // Kita akan menyederhanakan bagian anggota dan kegiatan
    // karena mungkin tidak perlu menampilkan detail ini di aplikasi Android

    // Konstruktor kosong (diperlukan untuk deserialisasi)
    public Daerah() {
    }

    // Konstruktor dengan semua field (opsional)
    public Daerah(String namaDaerah, String deskripsi) {
        this.namaDaerah = namaDaerah;
        this.deskripsi = deskripsi;
    }

    // Getter dan setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaDaerah() {
        return namaDaerah;
    }

    public void setNamaDaerah(String namaDaerah) {
        this.namaDaerah = namaDaerah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public List<Kegiatan> getKegiatanList() {
        return kegiatanList;
    }

    public List<Anggota> getAnggotaList() {
        return anggotaList;
    }

    public void setKegiatanList(List<Kegiatan> kegiatanList) {
        this.kegiatanList = kegiatanList;
    }

    public void setAnggotaList(List<Anggota> anggotaList) {
        this.anggotaList = anggotaList;
    }

    // toString() jika diperlukan
    @Override
    public String toString() {
        return namaDaerah;
    }
}

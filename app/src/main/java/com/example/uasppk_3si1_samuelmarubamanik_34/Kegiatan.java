package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.io.Serializable;

public class Kegiatan implements Serializable {
    private Long id;
    private Long daerahId;
    private String namaKegiatan;
    private String deskripsiKegiatan;

    private Daerah daerah;

    public Kegiatan() {
    }

    public Kegiatan(Long daerahId,String namaKegiatan, String deskripsiKegiatan) {
        this.daerahId = daerahId;
        this.namaKegiatan = namaKegiatan;
        this.deskripsiKegiatan = deskripsiKegiatan;
    }

    public void setDaerahId(Long daerahId) {
        this.daerahId = daerahId;
    }

    public Long getDaerahId() {
        return daerah != null ? daerah.getId() : null;
    }

    public Long getId() {
        return id;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getDeskripsiKegiatan() {
        return deskripsiKegiatan;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public void setDeskripsiKegiatan(String deskripsiKegiatan) {
        this.deskripsiKegiatan = deskripsiKegiatan;
    }
}

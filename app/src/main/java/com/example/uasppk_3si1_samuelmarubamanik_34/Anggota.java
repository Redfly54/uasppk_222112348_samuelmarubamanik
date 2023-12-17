package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.io.Serializable;

public class Anggota implements Serializable {
    private Long id;
    private Long daerahId;
    private String firstName;
    private String lastName;
    private String kelas;

    private Daerah daerah;

    public Anggota() {
    }

    public Anggota(Long daerahId, String firstName, String lastName, String kelas) {
        this.daerahId = daerahId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.kelas = kelas;
    }

    public Long getId() {
        return id;
    }

    public Long getDaerahId() {
        return daerah != null ? daerah.getId() : null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getKelas() {
        return kelas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDaerahId(Long daerahId) {
        this.daerahId = daerahId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }


}

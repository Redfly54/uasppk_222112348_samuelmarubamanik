package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface KegiatanService {

    @POST("/admin/kegiatan/add")
    Call<Void> tambahKegiatan(@Body Kegiatan kegiatan);

    // Mendapatkan daftar semua kegiatan
    @GET("/kegiatans")
    Call<List<Kegiatan>> getAllKegiatan();

    // Mengubah kegiatan yang sudah ada
    @PUT("/admin/kegiatans/{id}")
    Call<Void> updateKegiatan(@Path("id") Long id, @Body Kegiatan kegiatan);

    // Menghapus kegiatan tertentu
    @DELETE("/admin/kegiatans/{id}")
    Call<Void> deleteKegiatan(@Path("id") Long id);
}

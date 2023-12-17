package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AnggotaService {
    @POST("/admin/anggota/add")
    Call<Void> tambahAnggota(@Body Anggota anggota);

    @GET("/anggotas")
    Call<List<Anggota>> getAllAnggota();

    @PUT("/admin/anggotas/{id}")
    Call<Void> updateAnggota(@Path("id") Long id, @Body Anggota anggota);

    @DELETE("/admin/anggotas/{id}")
    Call<Void> deleteAnggota(@Path("id") Long id);
}

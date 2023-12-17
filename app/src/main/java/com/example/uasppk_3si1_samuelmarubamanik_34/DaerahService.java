package com.example.uasppk_3si1_samuelmarubamanik_34;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DaerahService {
    @POST("/admin/daerah/add")
    Call<Void> tambahDaerah(@Body Daerah daerah);

    @GET("/daerahs")
    Call<List<Daerah>> getAllDaerah();

    @GET("/daerahs/all")
    Call<List<Daerah>> getDaerahWithDetails();

    @GET("/daerahs/{id}")
    Call<Daerah> getDaerahById(@Path("id") Long id);

    @PUT("/admin/daerahs/{id}")
    Call<Void> updateDaerah(@Path("id") Long id, @Body Daerah daerah);

    @DELETE("/admin/daerahs/{id}")
    Call<Void> deleteDaerah(@Path("id") Long id);
}

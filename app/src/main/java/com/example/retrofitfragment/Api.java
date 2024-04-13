package com.example.retrofitfragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("users/{username}/repos")
    Call<List<Results>> getRepositorio(
            @Path("username") String username
    );
    @GET("repos/{username}/{repos}")
    Call<Results> getRepositorioInfo(
            @Path("username") String username, @Path("repos") String repos
    );







}
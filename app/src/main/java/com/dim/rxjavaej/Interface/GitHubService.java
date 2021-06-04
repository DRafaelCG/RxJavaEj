package com.dim.rxjavaej.Interface;

import com.dim.rxjavaej.model.GitHubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {
    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>>
    getStarredRepositories(@Path("user") String userName);
}

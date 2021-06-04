package com.dim.rxjavaej.model;

public class GitHubRepo {
    public final int id;
    public final String name;
    public final String description;
    public final String language;
    public final int stargazersCount;

    public GitHubRepo(int id, String name, String description, String language, int stargazersCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.language = language;
        this.stargazersCount = stargazersCount;
    }
}

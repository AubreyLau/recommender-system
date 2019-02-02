package com.child.model;

public class UserMovieReference {
    private Integer id;

    private Integer userId;

    private Integer refrence;

    private Long timestamp;

    private Integer movieId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRefrence() {
        return refrence;
    }

    public void setRefrence(Integer refrence) {
        this.refrence = refrence;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
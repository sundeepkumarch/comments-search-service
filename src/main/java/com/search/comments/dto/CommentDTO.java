package com.search.comments.dto;

import java.io.Serializable;

public class CommentDTO implements Serializable {

    private String userId;
    private String comment;

    public CommentDTO() {
    }

    public CommentDTO(String userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "userId='" + userId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

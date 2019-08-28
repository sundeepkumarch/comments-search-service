package com.search.comments.service;

import com.search.comments.dto.CommentDTO;
import com.search.comments.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentsService {

    void addComment(CommentDTO commentDTO);

    List<Comment> searchComments(String words);
}

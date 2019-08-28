package com.search.comments.es.repository;

import com.search.comments.entities.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CommentsSearchRepository extends ElasticsearchRepository<Comment, Long> {
    List<Comment> findByComment(String comment);
}

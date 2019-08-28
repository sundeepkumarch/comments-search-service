package com.search.comments.assembler;

import com.search.comments.dto.CommentDTO;
import com.search.comments.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentsAssembler {

    public Comment toDomainObj(CommentDTO dto){
        Comment entity = new Comment();
        if(dto != null){
            entity.setComment(dto.getComment());
        }
        return entity;
    }

    public CommentDTO fromDomainObj(Comment entity){
        CommentDTO dto = new CommentDTO();
        if(entity != null){
            dto.setComment(entity.getComment());
        }
        return dto;
    }
}

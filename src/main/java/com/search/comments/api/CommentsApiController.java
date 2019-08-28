package com.search.comments.api;

import com.search.comments.dto.CommentDTO;
import com.search.comments.es.repository.CommentsSearchRepository;
import com.search.comments.entities.Comment;
import com.search.comments.service.CommentsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentsApiController {

    private static final Logger LOGGER = LogManager.getLogger(CommentsApiController.class);

    @Autowired
    private CommentsService mService;

    @Autowired
    private CommentsSearchRepository commentsSearchRepository;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO){
        LOGGER.info("CommentDTO:: Comment:"+commentDTO.getComment());
        mService.addComment(commentDTO);
        return ResponseEntity.ok("Comment saved Successfully");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<List<Comment>> searchAll(){
        List<Comment> commentList = new ArrayList<>();
        Iterable<Comment> comments =  commentsSearchRepository.findAll();
        comments.forEach(commentList::add);
        return ResponseEntity.ok(commentList);
    }

    @RequestMapping(value = "/search/{comment}", method = RequestMethod.GET)
    ResponseEntity<List<Comment>> searchByComment(@PathVariable String comment){
        List<Comment> commentList = mService.searchComments(comment);
        return ResponseEntity.ok(commentList);
    }
}

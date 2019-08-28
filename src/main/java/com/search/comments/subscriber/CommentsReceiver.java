package com.search.comments.subscriber;

import com.search.comments.entities.Comment;
import com.search.comments.es.repository.CommentsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentsReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsReceiver.class);

    @Autowired
    private CommentsSearchRepository commentsSearchRepository;

    public void receiveMessage(Comment message) {

        LOGGER.info("Received Message: " + message.getComment());
        // save to Elastic Search
        commentsSearchRepository.save(message);
    }
}

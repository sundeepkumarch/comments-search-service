package com.search.comments.service;

import com.search.comments.assembler.CommentsAssembler;
import com.search.comments.dto.CommentDTO;
import com.search.comments.entities.Comment;
import com.search.comments.repository.CommentsRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseCommentsService implements CommentsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCommentsService.class);

    @Autowired
    private CommentsRepository mRepo;

    @Autowired
    private CommentsAssembler mAssembler;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Value("${spring.rabbitmq.queueName}")
    private String queue;

    @Value("${spring.rabbitmq.exchangeName}")
    private String exchange;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    /**
     * Function to persist the comment to H2 and publish to RabbitMQ
     *
     * @param commentDTO Comment object to be persisted
     */
    @Override
    public void addComment(CommentDTO commentDTO) {
        LOGGER.info("Comment:" + commentDTO.getComment());
        Comment entity = mAssembler.toDomainObj(commentDTO);

        LOGGER.info("Saving Comment to H2");
        mRepo.save(entity);

        LOGGER.info("Publishing Comment to RMQ");
        rabbitTemplate.convertAndSend(exchange, routingKey, entity);
    }

    /**
     * Function to search comments based in the given query word
     *
     * @param query String to be queried
     * @return the list of comments ordered by matching score
     */
    @Override
    public List<Comment> searchComments(String query) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("comment", query))
                .build();
        List<Comment> comments = elasticsearchTemplate.queryForList(searchQuery, Comment.class);
        for (Comment comment : comments) {
            LOGGER.info(comment.getComment());
        }
        return comments;
    }
}

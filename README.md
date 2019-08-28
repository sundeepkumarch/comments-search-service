## Comments Search Service
SpringBoot Service to search the best match comments using H2, RabbitMQ and Elastic Search

### Description
This Project loads the comments into Elastic and Searches. Using the following endpoints:
- `POST /comment` - saves the comment.
- `GET /search` - returns all the comments.
- `GET /search/{comment}` - returns comments matching the given comment.
### Dependencies
- Spring Boot
- Spring REST Controller
- Spring AMQP
- Spring Data Elastic Search
- Spring H2 Database
### Flow
1. When a comment is saved, the data is saved into an in-memory database(H2) and also published to a Message Queue(RabbitMQ). 
2. The Consumer/Subscriber of the MQ reads the comment and pushed into Elastic Search.
3. On search for a particular comment, the Elastic Search returns the list of matching comments in the order of matching score.

### Example:
**Input**

Comment1: _Daniel is good android developer_

Comment2: _William is an average android developer_

Comment3: _Jennifer is the best android designer_

Comment4: _Andrew is good mobile developer but doesnt know android_

Comment5: _Gary is the best ios designer_

Search String: _good android developer_

**Output**

1. Daniel is **good android developer**
2. Andrew is **good** mobile **developer** but doesnt know **android**
3. William is an average **android developer**
4. Jennifer is the best **android** designer


package com.search.comments.es.config;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.search.comments.es.repository")
public class ElasticConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticConfiguration.class);

    @Bean
    NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws IOException {
        File tempDir = File.createTempFile("elastic", Long.toString(System.nanoTime()));
        LOGGER.info("Temp directory: " + tempDir.getAbsolutePath());

        Settings.Builder elasticSearchSettings =
                Settings.settingsBuilder()
                        .put("http.enabled", "true")
                        .put("index.number_of_shards", "5")
                        .put("path.data", new File(tempDir, "data").getAbsolutePath())
                        .put("path.logs", new File(tempDir, "logs").getAbsolutePath())
                        .put("path.work", new File(tempDir, "work").getAbsolutePath())
                        .put("path.home", tempDir);

        return new ElasticsearchTemplate(nodeBuilder()
                .local(true)
                .settings(elasticSearchSettings.build())
                .node()
                .client());
    }

}

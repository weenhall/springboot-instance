package com.ween.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
@EnableConfigurationProperties(ElasticsearchRestClientProperties.class)
public class ESConfig extends AbstractElasticsearchConfiguration {

    private final ElasticsearchRestClientProperties properties;

    public ESConfig(ElasticsearchRestClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration=ClientConfiguration.builder()
                .connectedTo(this.properties.getUris().get(0))
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}

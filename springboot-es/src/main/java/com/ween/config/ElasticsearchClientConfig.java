package com.ween.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchClientConfig {

	@Value("${spring.elasticsearch.uris}")
	private String uris;

	@Bean
	public ElasticsearchClient elasticsearchClient(){
		RestClientBuilder builder= RestClient.builder(HttpHost.create(uris));
		ElasticsearchTransport transport=new RestClientTransport(builder.build(), new JacksonJsonpMapper());
		return new ElasticsearchClient(transport);
	}

}

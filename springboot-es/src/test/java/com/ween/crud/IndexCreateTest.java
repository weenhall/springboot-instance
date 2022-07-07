package com.ween.crud;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ween.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * create index using different method
 */
@Slf4j
@SpringBootTest
public class IndexCreateTest {

	@Autowired
	private ElasticsearchClient esClient;

	@Test
	public void usingFluentDSL() throws IOException {
		log.info("create Index usingFluentDSL");
		Product product=Product.builder()
				.sku("bike-1")
				.name("City bike")
				.price(123.0)
				.build();
		IndexResponse response=esClient.index(item->item
		.index("products")
		.id(product.getSku())
		.document(product));
		log.info("Indexed with version :{}",response.version());
	}

	@Test
	public void usingOfDSL(){
		log.info("create Index usingOfDSL");
		Product product=Product.builder()
				.sku("bike-1")
				.name("City bike")
				.price(123.0)
				.build();
		IndexRequest<Product> request=IndexRequest.of(item->item
		.index("products")
		.id(product.getSku())
		.document(product));
	}

	@Test
	public void usingClazzBuilder() throws IOException {
		log.info("create Index usingClazzBuilder");
		Product product=Product.builder()
				.sku("bike-1")
				.name("City bike")
				.price(123.0)
				.build();
		IndexRequest.Builder<Product> builder=new IndexRequest.Builder<>();
		builder.index("products");
		builder.id(product.getSku());
		builder.document(product);
		IndexResponse response=esClient.index(builder.build());
		log.info("Indexed with version:{}",response.version());
	}

	@Test
	public void usingJSONData() throws Exception {
		Product product=Product.builder()
				.sku("bike-1")
				.name("City bike")
				.price(123.0)
				.build();
		String jsonStr= new ObjectMapper().writeValueAsString(product);
		Reader input=new StringReader(jsonStr);
		IndexRequest<Product> request=IndexRequest.of(item->item.index("products").withJson(input));
		IndexResponse response=esClient.index(request);
		log.info("Indexed with version{}",response.version());
	}

	@Test
	public void bulkIndexData() throws IOException {
		List<Product> products=fetchProducts();
		BulkRequest.Builder builder=new BulkRequest.Builder();
		for (Product product:products){
			builder.operations(op->op
			.index(item->item
			.index("products")
			.id(product.getSku())
			.document(product)));
		}
		BulkResponse response=esClient.bulk(builder.build());
		if(response.errors()){
			log.info("Bulk had errors");
		}
	}

	public List<Product> fetchProducts(){
		List<Product> list=new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Product product=Product.builder()
					.sku("bike-"+i)
					.name("City bike"+i)
					.price(123.0+i)
					.build();
			list.add(product);
		}
		return list;
	}
}

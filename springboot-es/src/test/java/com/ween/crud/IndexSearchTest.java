package com.ween.crud;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import com.ween.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * search index with multiple method
 */
@Slf4j
@SpringBootTest
public class IndexSearchTest {

	@Autowired
	private ElasticsearchClient esClient;

	@Test
	public void simpleSearch() throws IOException {
		String searchText = "bike1";
		SearchResponse<Product> response = esClient.search(item -> item
				.index("products")
				.query(q -> q
						.match(t -> t
								.field("name")
								.query(searchText))
				), Product.class);

		TotalHits totalHits = response.hits().total();
		boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;
		if (isExactResult) {
			log.info("There are {} results", totalHits.value());
		} else {
			log.info("There are more than {} results", totalHits.value());
		}

		List<Hit<Product>> hits = response.hits().hits();
		for (Hit<Product> hit : hits) {
			Product product = hit.source();
			log.info("Found product {}", product.toString());
		}
	}

	@Test
	public void nestedSearch() throws IOException {
		String searchText = "bike";
		double maxPrice = 123.0;

		Query byName = MatchQuery.of(item -> item
				.field("name")
				.query(searchText))._toQuery();

		Query byMaxPrice = RangeQuery.of(item -> item
				.field("price")
				.gte(JsonData.of(maxPrice)))._toQuery();

		SearchResponse<Product> response = esClient.search(item -> item
				.index("products")
				.query(q->q
				.bool(b->b
				.must(byName)
				.must(byMaxPrice))), Product.class);
		List<Hit<Product>> hits=response.hits().hits();
		for (Hit<Product> hit : hits) {
			Product product = hit.source();
			log.info("Found product {}", product.toString());
		}
	}
}

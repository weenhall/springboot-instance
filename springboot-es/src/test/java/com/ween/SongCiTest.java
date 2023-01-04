package com.ween;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ween.model.SongCi;
import com.ween.repository.SongCiRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class SongCiTest {

	@Resource
	private SongCiRepository repository;
	@Autowired
	private ElasticsearchClient client;

	@Test
	public void loadData() throws IOException {
		URL url=this.getClass().getClassLoader().getResource("poetry/songci.json");
		assert url != null;
		File file=new File(url.getFile());
		ObjectMapper mapper=new ObjectMapper();
		List<SongCi> songCiList=mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class,SongCi.class));
		repository.saveAll(songCiList);
	}

	@Test
	public void search() throws Exception {
		String keyword="春天";
		String indexName="songci";

		TermQuery termQuery=new TermQuery.Builder().field("paragraphs").value(keyword).build();
		Query query=new Query(termQuery);

		Highlight highlight=new Highlight.Builder().fields("paragraphs", new HighlightField.Builder().preTags("<font color='red'>").postTags("</font>").build()).build();

		SearchRequest request=new SearchRequest.Builder()
				.index(indexName)
				.query(query)
				.highlight(highlight)
				.build();
		SearchResponse<SongCi> response=client.search(request,SongCi.class);

		log.info(String.valueOf(response.took()));
		assert response.hits().total() != null;
		log.info(String.valueOf(response.hits().total().value()));
		response.hits().hits().forEach(item->{
			assert item.source() != null;
			log.info(item.source().toString());
			for(Map.Entry<String,List<String>> entry:item.highlight().entrySet()){
				log.info("Key:{}",entry.getKey());
				entry.getValue().forEach(log::info);
			}
		});
	}
}

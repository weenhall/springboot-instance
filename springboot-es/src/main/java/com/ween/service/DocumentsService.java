package com.ween.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.ween.model.Documents;
import com.ween.repository.DocumentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DocumentsService {

	@Autowired
	private DocumentsRepository documentsRepository;
//	@Autowired
//	private ElasticsearchClient elasticsearchClient=new ElasticsearchClient();

	public String listAll() throws IOException {
//		SearchRequest request=new SearchRequest.Builder().build();
//		SearchResponse<Documents> response=elasticsearchClient.search(request,Documents.class);
//		List<String> strList=new ArrayList<>();
//		response.hits().hits().stream().forEach(item->{
//			strList.add(item.source().toString());
//		});
//		return String.join(",",strList);
		return null;
	}

	public List<Map<String,String>> search(String keyword){
		return null;
	}
}

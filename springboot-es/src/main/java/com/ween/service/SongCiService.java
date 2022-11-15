package com.ween.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.ml.Page;
import com.ween.model.SongCi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SongCiService {

	@Autowired
	private ElasticsearchClient client;

	public List<SongCi> search(String keyword, Pageable page) throws Exception {
		String indexName="songci";
		String field="paragraphs";
		if(page==null){
			page= PageRequest.of(0,5);
		}

		TermQuery termQuery=new TermQuery.Builder().field(field).value(keyword).build();
		Query query=new Query(termQuery);
		SortOptions sortOptions=new SortOptions.Builder().field(SortOptionsBuilders.field().field("author").order(SortOrder.Asc).build()).build();

		Highlight highlight=new Highlight.Builder().fields(field, new HighlightField.Builder().preTags("<font color='red'>").postTags("</font>").build()).build();
		SearchRequest request=new SearchRequest.Builder()
				.index(indexName)
				.query(query)
				.highlight(highlight)
				.from(page.getPageNumber()*page.getPageSize())
				.size(page.getPageSize())
				.sort(sortOptions)
				.build();
		SearchResponse<SongCi> response=client.search(request,SongCi.class);
		assert response.hits().total() != null;
		List<SongCi> result=new ArrayList<>((int) response.hits().total().value());
		response.hits().hits().forEach(item->{
			SongCi songCi=item.source();
			for(Map.Entry<String,List<String>> entry:item.highlight().entrySet()){
				//替换高亮文本
				entry.getValue().forEach(value->{
					String originValue=replace(value);
					assert songCi != null;
					int index=songCi.getParagraphs().indexOf(originValue);
					if(index>=0){
						songCi.getParagraphs().set(index,value);
					}
				});
			}
			result.add(songCi);
		});
		return result;
	}

	private String replace(String s1){
		return s1.replaceAll("<[^>]*>", "");
	}
}

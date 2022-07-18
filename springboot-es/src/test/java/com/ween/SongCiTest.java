package com.ween;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ween.model.SongCi;
import com.ween.repository.SongCiRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootTest
public class SongCiTest {

	@Resource
	private SongCiRepository repository;

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
	public void search(){

	}
}

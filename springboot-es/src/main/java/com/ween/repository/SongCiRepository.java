package com.ween.repository;

import com.ween.model.SongCi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongCiRepository extends ElasticsearchRepository<SongCi,String> {
}

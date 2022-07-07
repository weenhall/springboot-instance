package com.ween.repository;

import com.ween.model.Documents;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends ElasticsearchRepository<Documents,String> {
}

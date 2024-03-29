package com.ween.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@Document(indexName = "documents")
public class Documents {

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String name;

	@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
	private String content;

	@Field(type = FieldType.Keyword)
	private String ext;

}

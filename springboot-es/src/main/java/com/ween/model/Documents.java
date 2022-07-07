package com.ween.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "documents")
public class Documents {

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String name;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Keyword)
	private String ext;

}

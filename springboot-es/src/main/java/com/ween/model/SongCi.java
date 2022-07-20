package com.ween.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "songci")
public class SongCi {

	@Id
	private String id;
	@Field(type = FieldType.Keyword)
	private String author;
	@Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
	private List<String> paragraphs;
	@Field(type = FieldType.Keyword)
	private String rhythmic;
	@Field(type = FieldType.Keyword)
	private List<String> tags;
}

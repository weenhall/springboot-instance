package com.ween.springboothibernate.entity.associations.many2one;

import com.ween.springboothibernate.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package com.ween.springboothibernate.entity.associations.many2one;

import com.ween.springboothibernate.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "`number`")
	private String number;
	@ManyToOne
	@JoinColumn(name = "person_id",foreignKey = @ForeignKey(name = "PERSON_ID_FK"))
	private Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}

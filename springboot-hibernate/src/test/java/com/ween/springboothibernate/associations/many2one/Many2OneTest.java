package com.ween.springboothibernate.associations.many2one;

import com.ween.springboothibernate.entity.associations.many2one.Person;
import com.ween.springboothibernate.entity.associations.many2one.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class Many2OneTest {

	@PersistenceContext(unitName = "default")
	private EntityManager entityManager;

	@Test
	public void test(){
		entityManager.getTransaction().begin();
		Person person=new Person();
		person.setName("foo");
		entityManager.persist(person);

		Phone phone=new Phone();
		phone.setNumber("123-456-789");
		phone.setPerson(person);
		entityManager.persist(phone);

		entityManager.flush();

		phone.setPerson(null);
		entityManager.getTransaction().commit();
	}
}

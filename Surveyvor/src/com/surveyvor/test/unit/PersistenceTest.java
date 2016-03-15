package com.surveyvor.test.unit;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersistenceTest {

	private  static EntityManager em;
	private static EntityManagerFactory fact;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fact = Persistence.createEntityManagerFactory("myBase");
		em=fact.createEntityManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		em.getTransaction().begin();
	}

}
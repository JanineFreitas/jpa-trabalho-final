package br.edu.uni7.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExerciciosTest {
	
	private static EntityManager entityManager;

	@BeforeClass
	public static void startup(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("avaliacoes");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@Test
	public void todosProdutos() {
		TypedQuery<Produto> p = entityManager.createNamedQuery("Produto.todos", Produto.class);
		Assert.assertTrue(!p.getResultList().isEmpty());
	}
	
	@Test
	public void avaliacaoIssuesAcima3Votos() {
		TypedQuery<Avaliacao> avaliacao = entityManager.createNamedQuery("Avaliacao.issuesAcima3Votos", Avaliacao.class);
		Assert.assertNotNull(avaliacao.getResultList());
	}
}


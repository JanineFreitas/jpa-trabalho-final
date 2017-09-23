package br.edu.uni7.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExerciciosTest {

	private static EntityManager entityManager;

	@BeforeClass
	public static void startup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("avaliacoes");
		entityManager = entityManagerFactory.createEntityManager();
	}

	// 1º Consulta
	@Test
	public void todosProdutos() {
		TypedQuery<Produto> p = entityManager.createNamedQuery("Produto.todos", Produto.class);
		Assert.assertTrue(!p.getResultList().isEmpty());
	}

	// 2º Consulta
	// @Test
	// public void avaliacaoIssuesAcima3Votos() {
	// TypedQuery<Avaliacao> avaliacao =
	// entityManager.createNamedQuery("Avaliacao.issuesAcima3Votos",
	// Avaliacao.class);
	// Assert.assertNotNull(avaliacao.getResultList());
	// }
	
	//3º Consulta
	@SuppressWarnings("rawtypes")
	@Test
	public void produtoQtdeDebitosTecnicos() {
		Query createNamedQuery = entityManager.createNamedQuery("Produto.qtdeDebitosTecnicos").setParameter("idProduto", new Long(1));
		List resultList = createNamedQuery.getResultList();
		Assert.assertTrue(resultList!=null);
	}
	
	// 4º Consulta
	@Test
	public void departamentoMaiorQuantidadeAvaliacoes() {
		TypedQuery<Departamento> p = entityManager.createNamedQuery("Departamento.maiorQtdeAvaliacoes", Departamento.class);
		Assert.assertTrue(p.getSingleResult().getNome() != null);
		System.out.println("Id: "+p.getSingleResult().getId() +" nome: "+p.getSingleResult().getNome());
	}
}

package br.edu.uni7.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	@Test
	public void avaliacaoIssuesAcima3Votos() {
		TypedQuery<Avaliacao> avaliacao = entityManager.createNamedQuery("Avaliacao.issuesAcima3Votos", Avaliacao.class);
		Assert.assertNotNull(avaliacao.getResultList());
	}
	
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
	
	//Validações
	@Test
	public void nomeUsuarioNaoPodeSerNull() {
		Usuario user = new Usuario();
		user.setNome("");
		try {
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void nomeDepartamentoNaoPodeSerNull() {
		Departamento d = new Departamento();
		d.setNome("");
		try {
			entityManager.persist(d);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void departamentoDeProdutoNaoPodeSerNull() {
		Usuario user = new Usuario();
		user.setNome("Usuario 174");
		
		Produto p = new Produto();
		p.setNome("Produto 174");
		p.setResponsavel(user);
		p.setSituacao(Situacao.HABILITADO);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Assert.assertTrue(validator.validate(p).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.NotNull"));
		System.out.println("O departamento de produto não pode ser null.");
	}
	
	@Test
	public void responsavelPeloProdutoNaoPodeSerNull() {
		Departamento dep = new Departamento();
		dep.setNome("Departamento 174");
		
		Produto p = new Produto();
		p.setNome("Produto 174");
		p.setDepartamento(dep);
		p.setSituacao(Situacao.HABILITADO);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Assert.assertTrue(validator.validate(p).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.NotNull"));
		System.out.println("O resposável pelo produto não poder ser nulo.");
	}
	
	@Test
	public void produtoEAutorDaAvaliacaoNaoPodeSerNull() {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setData(new Date());
		avaliacao.setItensAvaliacao(new ArrayList<>());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Assert.assertTrue(validator.validate(avaliacao).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.NotNull") || validator.validate(avaliacao).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.Size.message"));
		System.out.println("Produto não pode ser null, Autor não pode ser null e a Avaliação tem que ter pelo menos um item.");
	}
	
	@Test
	public void comentarioESatatusDeItemAvaliacaoNaoPodeSerNull() {
		Issue item = new Issue();
		item.setStatus(null);
		item.setQuantidadeDeVotos(1);
		item.setComentario(gerarComentario());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Assert.assertTrue(validator.validate(item).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.NotNull"));
		System.out.println("O comentário não pode ser nulo, nem ser vazio e deve ter no máximo 500 caracteres.");
		System.out.println("O status não pode ser nulo.");
	}
	
	private String gerarComentario() {
		String alfabeto="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String comentario = "";
		Random generator = new Random(); 
		for (int i = 0; i < 600; i++) {
			comentario += alfabeto.charAt(generator.nextInt(alfabeto.length()));
		}
		return comentario;
	}
	
	@Test
	public void custoDebitoTecnico() {
		DebitoTecnico debito = new DebitoTecnico();
		debito.setComentario("comentario");
		debito.setImpacto(Impacto.ALTO);
		debito.setCusto(new Long(6));
		debito.setStatus(Status.ABERTO);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Assert.assertTrue(validator.validate(debito).iterator().next().getConstraintDescriptor().toString().contains("javax.validation.constraints.Max.message"));
	}
	
	//Eventos
	@Test
	public void dataAvaliacaoPreenchida() {
		Avaliacao ava = new Avaliacao();
		ava.setAutor(new Usuario());
		ava.setProduto(new Produto());
		ava.setItensAvaliacao(new ArrayList<>());
		ava.setData(new Date(14, 9, 1994));
		
		try {
			entityManager.persist(ava);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

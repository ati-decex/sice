package br.mil.eb.decex.ati.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import br.mil.eb.decex.ati.modelo.BaseModel;

/**
 * Repositório genérico para operações padrão de manipulação de dados dos 
 * objetos do modelo
 * 
 * @author Rodrigo Uchôa (http://rodrigouchoa.wordpress.com)
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.1
 *
 */
public class Repository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="sicePU")
	private EntityManager entityManager;
	
	public enum MatchMode { START, END, EXACT, ANYWHERE }
		
	public Repository() {
		
	}
	
	/**
	 * Construtor para utilização em testes unitários
	 * @param entityManager mecanismo de persistencia
	 */
	public Repository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
		
	/**
	 * Fechamento do entity manager para utilização em 
	 * testes unitários
	 */
	public void closeEntityManager() {
		entityManager.close();
	}
	
	public void flush() {
		entityManager.flush();
	}
			
	/**
	 * Adiciona um objeto ao repositório
	 * @param model objeto a adiconar
	 */
	public <T extends BaseModel<PK>, PK extends Serializable> void adicionar(T model) {
		entityManager.persist(model);
		entityManager.flush();
	}
		
	/**
	 * Muda os valores de um objeto no repositório
	 * @param model objeto mudado
	 */	
	public <T extends BaseModel<PK>, PK extends Serializable> void mudar(T model) {
		entityManager.merge(model);		 
	}	
		
	/**
	 * Adiciona ou muda os valores de um objeto no repositório
	 * @param model objeto adicionado ou mudado
	 */	
	public <T extends BaseModel<PK>, PK extends Serializable> void adicionarOuMudar(T model) {
		
		if(model.getId() != null)
			mudar(model);
		else
			adicionar(model);		
	}		
		
	/**
	 * Apaga um objeto no repositório
	 * @param model objeto apagado
	 */	
	public <T extends BaseModel<PK>, PK extends Serializable> void apagar(Class<T> clazz, PK id) {
		
		T model = encontrar(clazz, id);
		
		if(model != null)
			entityManager.remove(model);
		else
			throw new RuntimeException();
	}
	
	
    public <T extends BaseModel<PK>, PK extends Serializable> void apagarFlush(Class<T> clazz, PK id) {
		
		T model = encontrar(clazz, id);
		
		if(model != null){
			entityManager.remove(model);
		    entityManager.flush();
		}
		else
			throw new RuntimeException();
	}
	
	/**
	 * Apaga um objeto no repositório com chave composta
	 * @param model objeto apagado
	 */	
	public <T extends BaseModel<PK>, PK extends Serializable> void apagarPKComposta(Class<T> clazz, Serializable id) {
		
		T model = encontrar(clazz, id);
		
		if(model != null)
			entityManager.remove(model);
		else
			throw new RuntimeException();
	}			
		
		
	/**
	 * Encontra um ojbeto no repositório pelo identificador
	 * @param clazz tipo do objeto
	 * @param id identificador do objeto
	 * @return objeto recuperado do repositório
	 */
	public <T extends BaseModel<?>> T encontrar(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}
	
	/**
	 * Encontra todos os ojbetos no repositório pela propriedade informada
	 * @param clazz tipo do objeto
	 * @param nomeDaPropriedade no da propriedade
	 * @param valor valor da propriedade
	 * @return lista de objetos recuperados do repositório
	 */
	public <T extends BaseModel<?>> List<T> encontrar(Class<T> clazz, String nomeDaPropriedade, Object valor) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.equal(root.get(nomeDaPropriedade), valor));
		return entityManager.createQuery(cq).getResultList();
	}
	
	/**
	 * Encontra todos os ojbeto no repositório no modo like ignorando maiusculas ou minusculas pela  
	 * propriedade informada
	 * @param clazz tipo do objeto
	 * @param nomeDaPropriedade no da propriedade
	 * @param valor valor da propriedade
	 * @param matchMode modo da busca: EXACT, START, END, ANYWHERE
	 * @return lista de objetos recuperados do repositório
	 */
	public <T extends BaseModel<?>> List<T> encontrar(Class<T> clazz, String nomeDaPropriedade, String valor, MatchMode matchMode) {
		
		valor = valor.toLowerCase();
		
		if (MatchMode.START.equals(matchMode)) {
			valor = valor + "%";
		} else if (MatchMode.END.equals(matchMode)) {
			valor = "%" + valor;			
		} else if (MatchMode.ANYWHERE.equals(matchMode)) {
			valor = "%" + valor + "%";			
		}
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.like(cb.lower(root.get(nomeDaPropriedade)), valor));
				
		return entityManager.createQuery(cq).getResultList();
	}	
	
	/**
	 * Encontra todos os ojbetos no repositório pelo tipo
	 * @param clazz tipo do objeto
	 * @return lista de objetos recuperados do repositório
	 */
	public <T extends BaseModel<?>> List<T> encontrar(Class<T> clazz) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		cq.from(clazz);
				
		return entityManager.createQuery(cq).getResultList();
	}	
	
	/**
	 * Encontra todos os ojbetos no repositório pelo tipo com os resultados 
	 * ordenados pelas propriedades informadas
	 * @param clazz tipo do objeto
	 * @param order ASC ou DESC
	 * @param nomesDasPropriedades propriedades utilizadas para ordenação
	 * @return lista de objetos recuperados do repositório
	 */
	public <T extends BaseModel<?>> List<T> encontrar(Class<T> clazz, Order order, String... nomesDasPropriedades) {
						
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
				
		List<Order> orders = new ArrayList<>();
		
		for(String propridade : nomesDasPropriedades) 			
			if(order.isAscending())
				orders.add(cb.asc(root.get(propridade)));
			else
				orders.add(cb.desc(root.get(propridade)));			
		
		cq.orderBy(orders);
		
		return entityManager.createQuery(cq).getResultList();
	}

	/**
	 * Encontra uma lista de objetos no repositório através de uma query nomeada
	 * @param clazz tipo da lista
	 * @param namedQuery noma da query
	 * @return lista de objetos
	 */
	
	public <T extends BaseModel<?>> List<T> encontrarComNativa(Class<T> clazz, String namedQuery) {		
		return entityManager.createNativeQuery(namedQuery, clazz).getResultList();
	}
	
	
	public <T extends BaseModel<?>> List<T> encontrarComQueryNomeada(Class<T> clazz, String namedQuery) {		
		return entityManager.createNamedQuery(namedQuery, clazz).getResultList();
	}
	
	/**
	 * Encontra uma lista de objetos no repositório através de uma query nomeada com parametros
	 * @param clazz tipo da lista
	 * @param namedQuery noma da query
	 * @param values lista com pares nomeDaPropriedade/ValorDaPropriedade
	 * @return lista de objetos
	 */
	public <T extends BaseModel<?>> List<T> encontrarComQueryNomeada(Class<T> clazz, String namedQuery, Object[] ... values) {
		
		TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, clazz);
		
		for (Object[] strings : values) {
			query.setParameter(strings[0].toString(), strings[1]);
		}
		
		return query.getResultList();
	}		
}
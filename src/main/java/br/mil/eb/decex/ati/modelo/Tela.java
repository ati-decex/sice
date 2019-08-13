package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Tela para cadastramento dos tipos de efetivos. Classe para base
 * de montagem do wizard do sistema, uma vez que as telas são específicas 
 * por Organização militar
 * 
 * @author William <b>Moreira</b> de Pinho - Cap
 * @version 1.0
 *
 */

//query="SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.telas WHERE o.id = :id ORDER BY o.ordem")
@Entity
@Table(name="tela", uniqueConstraints={@UniqueConstraint(columnNames={"nome"})})
public class Tela extends BaseModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@SequenceGenerator(name="TELA_ID_GENERATOR", sequenceName="TELA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TELA_ID_GENERATOR")		
	private Long id;		
	
	@Column
	private String nome;
	
	@Column
	private String titulo;	
	
	@Column	
	private String caminho;
	
	@Column	
	private String tituloRelatorio;
	
	@Column	
	private String caminhoRelatorio;
	
	@Column
	private Integer ordem;
		
	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da obrigação
	 */
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Tela() {
		super();
	}
	
	public Tela(String nome) {
		super();
		this.nome = nome;
	}
	/**
	 * Nome da tela de controle do efetivo
	 * @return nome da tela de controle do efetivo 
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Descrição da tela
	 * @return descrição da tela
	 */
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Caminho JSF da tela
	 * @return caminho JSF da tela 
	 */
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	public String getCaminhoRelatorio() {
		return caminhoRelatorio;
	}
	
	public void setCaminhoRelatorio(String caminhoRelatorio) {
		this.caminhoRelatorio = caminhoRelatorio;
	}
	public String getTituloRelatorio() {
		return tituloRelatorio;
	}
	public void setTituloRelatorio(String tituloRelatorio) {
		this.tituloRelatorio = tituloRelatorio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tela other = (Tela) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
	   return this.nome;
	}
	
}
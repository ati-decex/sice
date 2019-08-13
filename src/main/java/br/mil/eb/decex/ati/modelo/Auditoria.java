package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import br.mil.eb.decex.ati.enumerado.Linha;
import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.Tipo;

/**
 * Auditoria do sistema sobre operação de CRUD realizadas 
 * pelo usuário. <p/>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 * @see br.mil.eb.decex.ati.model.Efetivo
 * @see br.mil.eb.decex.ati.model.OrganizacaoMilitar
 */
@Entity
public class Auditoria extends BaseModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUDITORIA_ID_GENERATOR", sequenceName="AUDITORIA_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUDITORIA_ID_GENERATOR")	
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private PostoGraduacao postoGraduacao;
		
	@Enumerated(EnumType.STRING)
	private Linha linha;
	
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column
	private Integer quantidade;
	
	@Column
	private Long organizacaoId;	
	
	@Column
	private Long usuarioId;	
	
	@Column
	private String metodo;
	
	public Auditoria(){
		
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da Auditoria
	 */		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de registro da operação
	 * @return data de registro da operação
	 */
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public PostoGraduacao getPostoGraduacao() {
		return postoGraduacao;
	}
	public void setPostoGraduacao(PostoGraduacao postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}

	public Linha getLinha() {
		return linha;
	}
	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	/**
	 * Organização do usuário responsável por inserir o efetivo
	 * @return organização do usuário que inseriu o efetivo
	 */
	public Long getOrganizacaoId() {
		return organizacaoId;
	}
	public void setOrganizacaoId(Long organizacaoId) {
		this.organizacaoId = organizacaoId;
	}
	
	/**
	 * Usuário responsável por inserir o efetivo
	 * @return usuário que inseriu o efetivo
	 */		
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auditoria other = (Auditoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Auditoria [id=" + id + ", data=" + data + ", metodo=" + metodo + "]";
	}	
}
package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Lançamento do documento referência do efetivo previsto de cada OMDS.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Qcp.porOrganizacao",
		query="SELECT q FROM QCP q WHERE q.organizacaoMilitar = :organizacao ORDER BY q.dataAlteracao"),
	@NamedQuery(name="Qcp.porOrganizacaoPorAtivo",
		query="SELECT q FROM QCP q WHERE q.organizacaoMilitar = :organizacao AND q.ativo = true ORDER BY q.dataAlteracao")
})
@Table(name="qcp")
public class QCP extends BaseModel<Long> implements Serializable,Comparable<QCP> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="QCP_ID_GENERATOR", sequenceName="QCP_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="QCP_ID_GENERATOR")		
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;
	
	@OneToMany(mappedBy="qcp", fetch=FetchType.EAGER)
	private List<EfetivoPrevisto> efetivosPrevistos;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organizacaoMilitar_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Column
	private Boolean ativo = true;

	public QCP() {
	}

	/**
	 * Guarda a informação de true ou false para o Ativo de 1 QCP 
	 * @return ativo Boolean
	 */
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

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
	
	/**
	 * Organização militar que possui o efetivo informado
	 * @return Organização militar que possui o efetivo informado
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	/**
	 * Date de alteração do QCP
	 * @return dataAlteracao
	 */
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	/**
	 * Lista todos os efetivos previstos por organização militar 
	 * @return lista de EfetivoPrevisto
	 */
	public List<EfetivoPrevisto> getEfetivosPrevistos() {
		if (efetivosPrevistos==null){
			efetivosPrevistos = new ArrayList<EfetivoPrevisto>();
		}
		return efetivosPrevistos;
	}
	public void setEfetivosPrevistos(List<EfetivoPrevisto> efetivosPrevistos) {
		this.efetivosPrevistos = efetivosPrevistos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataAlteracao == null) ? 0 : dataAlteracao.hashCode());
		result = prime * result + ((efetivosPrevistos == null) ? 0 : efetivosPrevistos.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
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
		QCP other = (QCP) obj;
		if (dataAlteracao == null) {
			if (other.dataAlteracao != null)
				return false;
		} else if (!dataAlteracao.equals(other.dataAlteracao))
			return false;
		if (efetivosPrevistos == null) {
			if (other.efetivosPrevistos != null)
				return false;
		} else if (!efetivosPrevistos.equals(other.efetivosPrevistos))
			return false;
		if (organizacaoMilitar == null) {
			if (other.organizacaoMilitar != null)
				return false;
		} else if (!organizacaoMilitar.equals(other.organizacaoMilitar))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QCP [dataAlteracao=" + dataAlteracao + ", efetivosPrevistos=" + efetivosPrevistos
				+ ", organizacaoMilitar=" + organizacaoMilitar + "]";
	}
	
	@Override
	public int compareTo(QCP qcp) {
		return this.getDataAlteracao().compareTo(qcp.getDataAlteracao());
	}

}

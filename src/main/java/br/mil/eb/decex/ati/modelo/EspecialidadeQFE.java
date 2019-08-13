package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Base para lançamento das disciplinas lecionadas pelos professores Civis.
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@Table(name="especialidadeQfe")
@NamedQueries({
	@NamedQuery(name = "EspecialidadeQfe.tipoQfe", query = "SELECT e FROM EspecialidadeQFE e WHERE e.tipoQfe= :tipoQfe ")})
public class EspecialidadeQFE extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ESPECIALIDADEQFE_ID_GENERATOR", sequenceName="ESPECIALIDADEQFE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESPECIALIDADEQFE_ID_GENERATOR")	
	private Long id;
	
	@Column
	private String descr_especialidade_qfe;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private TipoQFE tipoQfe;
	
	public EspecialidadeQFE() {
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

	public String getDescr_especialidade_qfe() {
		return descr_especialidade_qfe;
	}
	public void setDescr_especialidade_qfe(String descr_especialidade_qfe) {
		this.descr_especialidade_qfe = descr_especialidade_qfe;
	}

	public TipoQFE getTipoQfe() {
		return tipoQfe;
	}
	public void setTipoQfe(TipoQFE tipoQfe) {
		this.tipoQfe = tipoQfe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descr_especialidade_qfe == null) ? 0 : descr_especialidade_qfe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoQfe == null) ? 0 : tipoQfe.hashCode());
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
		EspecialidadeQFE other = (EspecialidadeQFE) obj;
		if (descr_especialidade_qfe == null) {
			if (other.descr_especialidade_qfe != null)
				return false;
		} else if (!descr_especialidade_qfe.equals(other.descr_especialidade_qfe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoQfe == null) {
			if (other.tipoQfe != null)
				return false;
		} else if (!tipoQfe.equals(other.tipoQfe))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EspecialidadeQFE [id=" + id + ", descr_especialidade_qfe=" + descr_especialidade_qfe + ", tipoQfe="
				+ tipoQfe + "]";
	}

	
	
}

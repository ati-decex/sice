package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Base para lançamento das disciplinas lecionadas pelos professores Civis.
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@Table(name="tipoQfe")
public class TipoQFE extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="TIPOQFE_ID_GENERATOR", sequenceName="TIPOQFE_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOQFE_ID_GENERATOR")	
	private Long id;
	
	@Column
	private String descr_tipo_qfe;
	
	public TipoQFE() {
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

	public String getDescr_tipo_qfe() {
		return descr_tipo_qfe;
	}
	public void setDescr_tipo_qfe(String descr_tipo_qfe) {
		this.descr_tipo_qfe = descr_tipo_qfe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descr_tipo_qfe == null) ? 0 : descr_tipo_qfe.hashCode());
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
		TipoQFE other = (TipoQFE) obj;
		if (descr_tipo_qfe == null) {
			if (other.descr_tipo_qfe != null)
				return false;
		} else if (!descr_tipo_qfe.equals(other.descr_tipo_qfe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoQFE [id=" + id + ", descr_tipo_qfe=" + descr_tipo_qfe + "]";
	}
	

	
}

package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.mil.eb.decex.ati.enumerado.LinhaEspecialidade;
import br.mil.eb.decex.ati.enumerado.PostoGraduacaoEspecialidade;

/**
 * Especialidade para cada Linha ou Tipo de Oficiais e Praças Temporários.
 * </p>
 * 
 * @author Fernando <b>Nunes</b> de Almeida - 1º Ten QCO
 * @version 1.0
 */


@Entity
@NamedQueries({
@NamedQuery(name = "Especialidade.pracasDeCarreira",     query = "SELECT e FROM Especialidade e WHERE e.linhaEspecialidade = 'CARR' AND postoGraduacaoEspecialidade = 'PRA' AND e.id not in (97)"),
@NamedQuery(name = "Especialidade.pracasTecTemporarios", query = "SELECT e FROM Especialidade e WHERE postoGraduacaoEspecialidade = 'PRA'  AND e.id not in (97) "),
@NamedQuery(name = "Especialidade.pracas", query = "SELECT e FROM Especialidade e WHERE postoGraduacaoEspecialidade = 'PRA' "),
@NamedQuery(name = "Especialidade.oficiaisPorLinha", query = "SELECT e FROM Especialidade e WHERE e.linhaEspecialidade = :linhaEspecialidade AND postoGraduacaoEspecialidade = :postograduacao "), 
@NamedQuery(name = "Especialidade.oficiaisPorLinhaQuaisQuer", query = "SELECT e FROM Especialidade e WHERE e.linhaEspecialidade = :linhaEspecialidade AND postoGraduacaoEspecialidade = :postograduacao AND e.id not in (96,97,105,59,277,278,101,284,283) "),
@NamedQuery(name = "Especialidade.oficiais", query = "SELECT e FROM Especialidade e WHERE e.linhaEspecialidade = :linhaEspecialidade AND postoGraduacaoEspecialidade = :postograduacao AND e.id not in (287,288,289) "), 
@NamedQuery(name = "Especialidade.PorLinhaParaOExistente", query = "SELECT e FROM Especialidade e WHERE e.linhaEspecialidade = :linhaEspecialidade AND postoGraduacaoEspecialidade = :postograduacao AND e.id != 101")})
@Table(name = "especialidade", uniqueConstraints = {@UniqueConstraint(columnNames = { "postograduacaoespecialidade","linhaespecialidade","nomeespecialidade" })})
public class Especialidade extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ESPECIALIDADE_ID_GENERATOR", sequenceName = "ESPECIALIDADE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESPECIALIDADE_ID_GENERATOR")
	private Long id;

	@NotNull
	@Column
	private String nomeEspecialidade;

	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private LinhaEspecialidade linhaEspecialidade;
	
	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private PostoGraduacaoEspecialidade postoGraduacaoEspecialidade;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEspecialidade() {
		if (nomeEspecialidade == null){
			nomeEspecialidade = "";
		}
		return nomeEspecialidade;
	}

	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public LinhaEspecialidade getLinhaEspecialidade() {
		return linhaEspecialidade;
	}

	public void setLinhaEspecialidade(LinhaEspecialidade linhaEspecialidade) {
		this.linhaEspecialidade = linhaEspecialidade;
	}

	public PostoGraduacaoEspecialidade getPostoGraduacaoEspecialidade() {
		return postoGraduacaoEspecialidade;
	}

	public void setPostoGraduacaoEspecialidade(PostoGraduacaoEspecialidade postoGraduacaoEspecialidade) {
		this.postoGraduacaoEspecialidade = postoGraduacaoEspecialidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linhaEspecialidade == null) ? 0 : linhaEspecialidade.hashCode());
		result = prime * result + ((nomeEspecialidade == null) ? 0 : nomeEspecialidade.hashCode());
		result = prime * result + ((postoGraduacaoEspecialidade == null) ? 0 : postoGraduacaoEspecialidade.hashCode());
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
		Especialidade other = (Especialidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linhaEspecialidade != other.linhaEspecialidade)
			return false;
		if (nomeEspecialidade == null) {
			if (other.nomeEspecialidade != null)
				return false;
		} else if (!nomeEspecialidade.equals(other.nomeEspecialidade))
			return false;
		if (postoGraduacaoEspecialidade != other.postoGraduacaoEspecialidade)
			return false;
		return true;
	}

	

}
package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Base de lançamento para cadastro da tela de ensino a distancia
 * </p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "EnsinoADistancia.VisualizacaoAnual", query = "SELECT o FROM EnsinoADistancia o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
		@NamedQuery(name = "ensinoADistancia.listarPorOMEPeriodo", query = "SELECT o FROM EnsinoADistancia o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao") })
@Table(name = "ensinoADistancia", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "periodo_id", "organizacao_id" }) })
public class EnsinoADistancia extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ENSINOADISTANCIA_ID_GENERATOR", sequenceName = "ENSINOADISTANCIA_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENSINOADISTANCIA_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "periodo_id")
	private Periodo periodo;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;

	@Column
	private Integer alunoOf;

	@Column
	private Integer alunoPr;

	@Column
	private Integer alunoCM;

	public EnsinoADistancia() {

	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Organização militar que possui o efetivo informado
	 * 
	 * @return Ooganização militar que possui o efetivo informado
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	/**
	 * Período de lançamento do efetivo. Cada efetivo é lançado dentro de um
	 * período.
	 * 
	 * @see br.mil.eb.decex.ati.modelo.Periodo
	 * @return período de lançamento
	 */
	public Periodo getPeriodo() {
		return periodo;
	}

	public Integer getAlunoOf() {
		return alunoOf;
	}

	/**
	 * Quantidade de alunos Oficial
	 * 
	 * @return quantidade de Alunos Oficial
	 */
	public void setAlunoOf(Integer alunoOf) {
		this.alunoOf = alunoOf;
	}

	public Integer getAlunoPr() {
		return alunoPr;
	}

	/**
	 * Quantidade de alunos Praça
	 * 
	 * @return quantidade de Alunos Praça
	 */
	public void setAlunoPr(Integer alunoPr) {
		this.alunoPr = alunoPr;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((alunoPr == null) ? 0 : alunoPr.hashCode());
		result = prime * result + ((alunoOf == null) ? 0 : alunoOf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EnsinoADistancia))
			return false;
		EnsinoADistancia other = (EnsinoADistancia) obj;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getAlunoPr() != other.getAlunoPr())
			return false;
		if (getAlunoOf() != other.getAlunoOf())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EnsinoADistancia [organizacaoMilitar=" + organizacaoMilitar + ", alunoPr=" + alunoPr + ", alunoOf="
				+ alunoOf + "]";
	}

	public Integer getAlunoCM() {
		return alunoCM;
	}

	public void setAlunoCM(Integer alunoCM) {
		this.alunoCM = alunoCM;
	}

}

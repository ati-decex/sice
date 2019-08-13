package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.mil.eb.decex.ati.enumerado.TipoProfessorMilitar;

/**
 * Base para lançamento dos Professores Militar.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ProfessorMilitar.VisualizacaoAnual", query = "SELECT o FROM ProfessorMilitar o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="professorMilitar.listarPorOMEPeriodo",
                query="SELECT o FROM ProfessorMilitar o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND tipoProfessorMilitar = :tipoProfessorMilitar" ),
    @NamedQuery(name="professorMilitar.listarPorPeriodo",
    			query="SELECT o FROM ProfessorMilitar o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND tipoProfessorMilitar != 'QFE' "),
    @NamedQuery(name="professorMilitar.listarPorOMEPeriodoSemTipo",
    query="SELECT o FROM ProfessorMilitar o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao")
})
@Table(name="professorMilitar", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "organizacao_id","tipoprofessormilitar"})})
public class ProfessorMilitar extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROFESSORMILITAR_ID_GENERATOR", sequenceName="PROFESSORMILITAR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROFESSORMILITAR_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Enumerated(EnumType.STRING)
	private TipoProfessorMilitar tipoProfessorMilitar;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public ProfessorMilitar() {

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
	 * Período de lançamento do efetivo. Cada efetivo é lançado dentro de um período.
	 * @see br.mil.eb.decex.ati.modelo.Periodo
	 * @return período de lançamento
	 */
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
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
	 * Valor de Efetivo masculino
	 * @return masculino
	 */
	public Integer getMasculino() {
		return masculino;
	}
	public void setMasculino(Integer masculino) {
		this.masculino = masculino;
	}
	
	/**
	 * Valor de Efetivo feminino
	 * @return feminino
	 */
	public Integer getFeminino() {
		return feminino;
	}
	public void setFeminino(Integer feminino) {
		this.feminino = feminino;
	}

	/**
	 * Tipo do professor militar
	 * @see br.mil.eb.decex.ati.enumerado.TipoProfessorMilitar
	 * @return TipoProfessorMilitar
	 */
	public TipoProfessorMilitar getTipoProfessorMilitar() {
		return tipoProfessorMilitar;
	}
	public void setTipoProfessorMilitar(TipoProfessorMilitar tipoProfessorMilitar) {
		this.tipoProfessorMilitar = tipoProfessorMilitar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((tipoProfessorMilitar == null) ? 0 : tipoProfessorMilitar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ProfessorMilitar))
			return false;
		ProfessorMilitar other = (ProfessorMilitar) obj;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getTipoProfessorMilitar() != other.getTipoProfessorMilitar())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProfessorMilitar [organizacaoMilitar=" + organizacaoMilitar + ", tipoProfessorMilitar=" + tipoProfessorMilitar 
				+ ", masculino=" + masculino + ", feminino=" + feminino + "]";
	}

}

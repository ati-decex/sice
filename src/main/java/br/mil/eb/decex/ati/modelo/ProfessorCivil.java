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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.mil.eb.decex.ati.enumerado.Carreira;
import br.mil.eb.decex.ati.enumerado.Escolaridade;
import br.mil.eb.decex.ati.enumerado.RegimeDeTrabalho;

/**
 * Base para lançamento dos Professores Civis.
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ProfessorCivil.VisualizacaoAnual", query = "SELECT o FROM ProfessorCivil o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="professorCivil.listarPorOMEPeriodo",
                query="SELECT o FROM ProfessorCivil o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao"),
    @NamedQuery(name="professorCivil.listarPorTodos",
    			query="SELECT o FROM ProfessorCivil o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND disciplina = :disciplina AND escolaridade = :escolaridade "
    					+ "AND regimeDeTrabalho = :regimeDeTrabalho AND carreira = :carreira ")
})
@Table(name="professorCivil", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "organizacao_id","carreira","disciplina_id","escolaridade","regimedetrabalho"})})
public class ProfessorCivil extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALUNOSMILITAROUTRASDORCAS_ID_GENERATOR", sequenceName="ALUNOSMILITAROUTRASDORCAS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALUNOSMILITAROUTRASDORCAS_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@OneToOne(optional=true,fetch=FetchType.EAGER)
	@JoinColumn(name="disciplina_id")
	private Disciplina disciplina;
	

	@Enumerated(EnumType.STRING)
	private Escolaridade escolaridade;

	@Enumerated(EnumType.STRING)
	private RegimeDeTrabalho regimeDeTrabalho;
		
	@Enumerated(EnumType.STRING)
	private Carreira carreira;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public ProfessorCivil() {

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
	 * Carrega a disciplina com valores do Enum disciplina
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Disciplina
	 */
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	/**
	 * Carrega a escolaridade com valores do Enum escolaridade
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Escolaridade
	 */
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	/**
	 * Carrega a regimeDeTrabalho com valores do Enum regimeDeTrabalho
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf RegimeDeTrabalho
	 */
	public RegimeDeTrabalho getRegimeDeTrabalho() {
		return regimeDeTrabalho;
	}
	public void setRegimeDeTrabalho(RegimeDeTrabalho regimeDeTrabalho) {
		this.regimeDeTrabalho = regimeDeTrabalho;
	}

	/**
	 * Carrega a carreira com valores do Enum carreira
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Carreira
	 */
	public Carreira getCarreira() {
		return carreira;
	}
	public void setCarreira(Carreira carreira) {
		this.carreira = carreira;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ProfessorCivil))
			return false;
		ProfessorCivil other = (ProfessorCivil) obj;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getPeriodo() != other.getPeriodo())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlunosMilitarOutrasForcas [organizacaoMilitar=" + organizacaoMilitar
				+ ", disciplina=" + disciplina + ", regimeDeTrabalho=" + regimeDeTrabalho
				+ ", masculino=" + masculino + ", feminino=" + feminino + ", carreira=" + carreira + ", escolaridade=" + escolaridade + "]";
	}


}

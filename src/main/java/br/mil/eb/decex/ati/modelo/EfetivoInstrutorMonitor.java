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

import br.mil.eb.decex.ati.enumerado.ForcaInstrutores;
import br.mil.eb.decex.ati.enumerado.TipoInstrutorEMonitor;

/**
 * Base para lançamento dos Instrutore e Monitores.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */ 
@Entity
@NamedQueries({
	@NamedQuery(name = "EfetivoInstrutorMonitor.VisualizacaoAnual", query = "SELECT o FROM EfetivoInstrutorMonitor o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="efetivoInstrutorMonitor.listarPorOMEPeriodo",
                query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND TipoInstrutorEMonitor = :TipoInstrutorEMonitor"),
    @NamedQuery(name="efetivoInstrutorMonitor.listarPorOm",
				query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND organizacaoMilitarSuperior = :organizacao AND organizacaoMilitar =:organizacaoM"),
    @NamedQuery(name="efetivoInstrutorMonitor.listarPorPeriodoEOm",
				query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND organizacaoMilitarSuperior = :organizacao"),
    @NamedQuery(name="efetivoInstrutorMonitor.listarPorForca",
				query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND forcaInstrutores = :forcaInstrutores"),
    @NamedQuery(name="efetivoInstrutorMonitor.listarPorForcaEPorSuperior",
	query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND  organizacaoMilitarSuperior = :organizacaoSuperior AND organizacaoMilitar = :organizacaoSubordinada AND forcaInstrutores = :forcaInstrutores")
,
@NamedQuery(name="efetivoInstrutorMonitor.listarPorOMSuperiorEPeriodo",
query="SELECT o FROM EfetivoInstrutorMonitor o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao"),
})
@Table(name="instrutorMonitor", uniqueConstraints={@UniqueConstraint(columnNames={"forcainstrutores", "tipoinstrutoremonitor", "organizacao_id" ,  "organizacao_superior_id" ,  "periodo_id"})})
public class EfetivoInstrutorMonitor extends BaseModel<Long> implements Serializable {

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
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_superior_id")
	private OrganizacaoMilitar organizacaoMilitarSuperior;
	
	@Enumerated(EnumType.STRING)
	private TipoInstrutorEMonitor tipoInstrutorEMonitor;
	
	@Enumerated(EnumType.STRING)
	private ForcaInstrutores forcaInstrutores;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public EfetivoInstrutorMonitor() {

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

	/**Organização militar que possui o efetivo informado com o superior
	 * @return Organização militar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitarSuperior() {
		return organizacaoMilitarSuperior;
	}
	public void setOrganizacaoMilitarSuperior(OrganizacaoMilitar organizacaoMilitarSuperior) {
		this.organizacaoMilitarSuperior = organizacaoMilitarSuperior;
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
	 * Valor de Efetivo instrutor
	 * @return instrutor
	 */
	public Integer getMasculino() {
		return masculino;
	}
	public void setMasculino(Integer masculino) {
		this.masculino = masculino;
	}
	
	/**
	 * Valor de Efetivo monitor
	 * @return monitor
	 */
	public Integer getFeminino() {
		return feminino;
	}
	public void setFeminino(Integer feminino) {
		this.feminino = feminino;
	}
	
	/**
	 * Carrega a TipoAlunosInstrutorEMonitor com valores do Enum TipoAlunosInstrutorEMonitor
	 * @see br.mil.eb.decex.ati.enumerado.TipoAlunosInstrutorEMonitor
	 * @returnf TipoAlunosInstrutorEMonitor
	 */
	public TipoInstrutorEMonitor getTipoInstrutorEMonitor() {
		return tipoInstrutorEMonitor;
	}
	public void setTipoInstrutorEMonitor(TipoInstrutorEMonitor tipoInstrutorEMonitor) {
		this.tipoInstrutorEMonitor = tipoInstrutorEMonitor;
	}

	/**
	 * Carrega a ForcaInstrutores com valores do Enum ForcaInstrutores
	 * @see br.mil.eb.decex.ati.enumerado.ForcaInstrutores
	 * @returnf ForcaInstrutores
	 */
	public ForcaInstrutores getForcaInstrutores() {
		return forcaInstrutores;
	}
	public void setForcaInstrutores(ForcaInstrutores forcaInstrutores) {
		this.forcaInstrutores = forcaInstrutores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((organizacaoMilitarSuperior == null) ? 0 : organizacaoMilitarSuperior.hashCode());
		result = prime * result + ((tipoInstrutorEMonitor == null) ? 0 : tipoInstrutorEMonitor.hashCode());
		result = prime * result + ((forcaInstrutores == null) ? 0 : forcaInstrutores.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EfetivoInstrutorMonitor))
			return false;
		EfetivoInstrutorMonitor other = (EfetivoInstrutorMonitor) obj;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getOrganizacaoMilitarSuperior() != other.getOrganizacaoMilitarSuperior())
			return false;
		if (getTipoInstrutorEMonitor() != other.getTipoInstrutorEMonitor())
			return false;
		if (getForcaInstrutores() != other.getForcaInstrutores())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EfetivoInstrutorMonitor [organizacaoMilitar=" + organizacaoMilitar + ",organizacaoMilitarSuperior=" + organizacaoMilitarSuperior + ", masculino=" + masculino
				+ ", feminino=" + feminino + ", masculino=" + masculino
				+ ", tipoInstrutorEMonitor=" + tipoInstrutorEMonitor+ "]";
	}

}

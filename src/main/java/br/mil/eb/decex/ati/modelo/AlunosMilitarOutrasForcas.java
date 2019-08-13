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

import br.mil.eb.decex.ati.enumerado.Forca;
import br.mil.eb.decex.ati.enumerado.TipoAlunosMilitarOutrasForcas;

/**
 * Base para lançamento dos Alunos Militares de outras forças.
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "AlunosMilitarOutrasForcas.VisualizacaoAnual", query = "SELECT o FROM AlunosMilitarOutrasForcas o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="alunosMilitarOutrasForcas.listarPorOMEPeriodo",
                query="SELECT o FROM AlunosMilitarOutrasForcas o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND forca = :forca AND tipoAlunosMilitarOutrasForcas = :tipoAlunosMilitarOutrasForcas"),
    @NamedQuery(name="alunosMilitarOutrasForcas.listarPorFoca",
    query="SELECT o FROM AlunosMilitarOutrasForcas o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND forca = :forca "),
    @NamedQuery(name="alunosMilitarOutrasForcas.listarPorPeriodo",
    query="SELECT o FROM AlunosMilitarOutrasForcas o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao")
})
@Table(name="alunosMilitarOutrasForcas", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "tipoalunosmilitaroutrasforcas", "forca", "organizacao_id"})})
public class AlunosMilitarOutrasForcas extends BaseModel<Long> implements Serializable {

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
	
	@Enumerated(EnumType.STRING)
	private Forca forca;
	
	@Enumerated(EnumType.STRING)
	private TipoAlunosMilitarOutrasForcas tipoAlunosMilitarOutrasForcas;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public AlunosMilitarOutrasForcas() {

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
	 * Carrega a forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public Forca getForca() {
		return forca;
	}
	public void setForca(Forca forca) {
		this.forca = forca;
	}

	/**
	 * Carrega a forca com valores do Enum tipoAlunosMilitarOutrasForcas
	 * @see br.mil.eb.decex.ati.enumerado.TipoAlunosMilitarOutrasForcas
	 * @returnf TipoAlunosMilitarOutrasForcas
	 */
	public TipoAlunosMilitarOutrasForcas getTipoAlunosMilitarOutrasForcas() {
		return tipoAlunosMilitarOutrasForcas;
	}
	public void setTipoAlunosMilitarOutrasForcas(TipoAlunosMilitarOutrasForcas tipoAlunosMilitarOutrasForcas) {
		this.tipoAlunosMilitarOutrasForcas = tipoAlunosMilitarOutrasForcas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((forca == null) ? 0 : forca.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result
				+ ((tipoAlunosMilitarOutrasForcas == null) ? 0 : tipoAlunosMilitarOutrasForcas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AlunosMilitarOutrasForcas))
			return false;
		AlunosMilitarOutrasForcas other = (AlunosMilitarOutrasForcas) obj;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getForca() != other.getForca())
			return false;
		if (getTipoAlunosMilitarOutrasForcas() != other.getTipoAlunosMilitarOutrasForcas())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlunosMilitarOutrasForcas [organizacaoMilitar=" + organizacaoMilitar
				+ ", forca=" + forca + ", tipoAlunosMilitarOutrasForcas=" + tipoAlunosMilitarOutrasForcas
				+ ", masculino=" + masculino + ", feminino=" + feminino + "]";
	}

}
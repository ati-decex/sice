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

import br.mil.eb.decex.ati.enumerado.Serie;

/**
 * Base de lançamento para cadastro dos alunos dos colegios militares</p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "AlunosColegioMilitar.VisualizacaoAnual", query = "SELECT o FROM AlunosColegioMilitar o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="alunosColegioMilitar.listarPorOMEPeriodo",
                query="SELECT o FROM AlunosColegioMilitar o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND serie = :serie"),
    @NamedQuery(name="alunosColegioMilitar.listarPorPeriodo",
    			query="SELECT o FROM AlunosColegioMilitar o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao")
})
@Table(name="alunosColegioMilitar" , uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "serie", "organizacao_id"})})
public class AlunosColegioMilitar extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALUNOSCOLEGIOMILITAR_ID_GENERATOR", sequenceName="ALUNOSCOLEGIOMILITAR_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALUNOSCOLEGIOMILITAR_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;

	@Enumerated(EnumType.STRING)
	private Serie serie;
	
	@Column
	private Integer processoSeletivo;
	
	@Column
	private Integer r69;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	@Column
	private Integer civis;
	
	@Column
	private Integer eb;
	
	@Column
	private Integer forcas;
	
	@Column
	private Integer forcasAuxiliares;
	
	@Column
	private Integer NEMasculino;
	
	@Column
	private Integer NEFeminino;
	
	@Column
	private Integer NAMasculino;
	
	@Column
	private Integer NAFeminino;
	
	public AlunosColegioMilitar() {

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
	 *Guarda a quantidade de Efetivo masculino de alunos de colegio militares
	 * @return masculino
	 */
	public Integer getMasculino() {
		return masculino;
	}
	public void setMasculino(Integer masculino) {
		this.masculino = masculino;
	}

	/**
	 *Guarda a quantidade de Efetivo feminino de alunos de colegio militares
	 * @return feminino
	 */
	public Integer getFeminino() {
		return feminino;
	}
	public void setFeminino(Integer feminino) {
		this.feminino = feminino;
	}

	/**
	 * Enumerador de serie dos alunos do colegio militar
	 *mostra todas as series disponiveis para os alunos.
	 * @return serie
	 */
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	/**
	 * quantidade de alunos aprovado por processo seletivo para o colegio militar.
	 * @return ProcessoSeletivo
	 */
	public Integer getProcessoSeletivo() {
		return processoSeletivo;
	}
	public void setProcessoSeletivo(Integer processoSeletivo) {
		this.processoSeletivo = processoSeletivo;
	}

	/**
	 * Quantidade de alunos aporovado pelo Regulamento do Exército processo-R69
	 * @return r69
	 */
	public Integer getR69() {
		return r69;
	}
	public void setR69(Integer r69) {
		this.r69 = r69;
	}

	/**
	 * Quantidade de alunos civis matriculados.
	 * @return civis
	 */
	public Integer getCivis() {
		return civis;
	}
	public void setCivis(Integer civis) {
		this.civis = civis;
	}

	/**
	 * quantidades de alunos militares do EB
	 * @return eb
	 */
	public Integer getEb() {
		return eb;
	}
	public void setEb(Integer eb) {
		this.eb = eb;
	}

	/**
	 *Quantidade de alunos militares de outras forças
	 * @return forca
	 */
	public Integer getForcas() {
		return forcas;
	}
	public void setForcas(Integer forcas) {
		this.forcas = forcas;
	}

	/**
	 * Quantidads de alunos militares de forças auxiliares
	 * @return fforcaAuxiliar
	 */
	public Integer getForcasAuxiliares() {
		return forcasAuxiliares;
	}
	public void setForcasAuxiliares(Integer forcasAuxiliares) {
		this.forcasAuxiliares = forcasAuxiliares;
	}

	/**
	 *Quantidades de alunos masculinos com nescessidades especiais
	 * @return NEMasculino
	 */
	public Integer getNEMasculino() {
		return NEMasculino;
	}
	public void setNEMasculino(Integer nEMasculino) {
		NEMasculino = nEMasculino;
	}
	
	/**
	 *Quantidades de alunas femininas com nescessidades especiais
	 * @return NEFeminino
	 */
	public Integer getNEFeminino() {
		return NEFeminino;
	}
	public void setNEFeminino(Integer nEFeminino) {
		NEFeminino = nEFeminino;
	}
	
	/**
	 * Quantidade de alunos masculinos de nações amigas 
	 * @return NaMasculino
	 */
	public Integer getNAMasculino() {
		return NAMasculino;
	}
	public void setNAMasculino(Integer nAMasculino) {
		NAMasculino = nAMasculino;
	}

	/**
	 *Quantidade de alunos femininas de nações amigas 
	 * @return NaFeminino
	 */
	public Integer getNAFeminino() {
		return NAFeminino;
	}
	public void setNAFeminino(Integer nAFeminino) {
		NAFeminino = nAFeminino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAFeminino == null) ? 0 : NAFeminino.hashCode());
		result = prime * result + ((NAMasculino == null) ? 0 : NAMasculino.hashCode());
		result = prime * result + ((NEFeminino == null) ? 0 : NEFeminino.hashCode());
		result = prime * result + ((NEMasculino == null) ? 0 : NEMasculino.hashCode());
		result = prime * result + ((civis == null) ? 0 : civis.hashCode());
		result = prime * result + ((eb == null) ? 0 : eb.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((forcas == null) ? 0 : forcas.hashCode());
		result = prime * result + ((forcasAuxiliares == null) ? 0 : forcasAuxiliares.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((processoSeletivo == null) ? 0 : processoSeletivo.hashCode());
		result = prime * result + ((r69 == null) ? 0 : r69.hashCode());
		result = prime * result + ((serie == null) ? 0 : serie.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AlunosColegioMilitar))
			return false;
		AlunosColegioMilitar other = (AlunosColegioMilitar) obj;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getNAFeminino() != other.getNAFeminino())
			return false;
		if (getNAMasculino() != other.getNAMasculino())
			return false;
		if (getNEFeminino() != other.getNEFeminino())
			return false;
		if (getNEMasculino() != other.getNEMasculino())
			return false;
		if (getCivis() != other.getCivis())
			return false;
		if (getEb() != other.getEb())
			return false;
		if (getForcas() != other.getForcas())
			return false;
		if (getForcasAuxiliares() != other.getForcasAuxiliares())
			return false;
		if (getProcessoSeletivo() != other.getProcessoSeletivo())
			return false;
		if (getR69() != other.getR69())
			return false;
		if (getSerie() != other.getSerie())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlunosColegioMilitar [organizacaoMilitar=" + organizacaoMilitar + ", serie=" + serie
				+ ", processoSeletivo=" + processoSeletivo + ", r69=" + r69 + ", masculino=" + masculino + ", feminino="
				+ feminino + ", civis=" + civis + ", eb=" + eb + ", forcas=" + forcas + ", forcasAuxiliares="
				+ forcasAuxiliares + ", NEMasculino=" + NEMasculino + ", NEFeminino=" + NEFeminino + ", NAMasculino="
				+ NAMasculino + ", NAFeminino=" + NAFeminino + "]";
	}
	
}
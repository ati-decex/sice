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

import br.mil.eb.decex.ati.enumerado.TipoEscolaridade;

/**
 * Base de lançamento para cadastro dos alunos civis dos colegios militares</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT STT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "AlunosCivis.VisualizacaoAnual", query = "SELECT o FROM AlunosCivis o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="alunosCivis.listarPorOMEPeriodoETipoEscolaridade",
                query="SELECT o FROM AlunosCivis o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND tipoEscolaridade = :tipoEscolaridade"),
    @NamedQuery(name="alunosCivis.listarPorOMEPeriodo",
    			query="SELECT o FROM AlunosCivis o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao")
})
@Table(name="alunosCivis" , uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "tipoescolaridade", "organizacao_id"})})
public class AlunosCivis  extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALUNOSCIVIS_ID_GENERATOR", sequenceName="ALUNOSCIVIS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALUNOSCIVIS_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;

	@Enumerated(EnumType.STRING)
	private TipoEscolaridade tipoEscolaridade;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public AlunosCivis() {

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
	public TipoEscolaridade getTipoEscolaridade() {
		return tipoEscolaridade;
	}
	public void setTipoEscolaridade(TipoEscolaridade tipoEscolaridade) {
		this.tipoEscolaridade = tipoEscolaridade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipoEscolaridade == null) ? 0 : tipoEscolaridade.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AlunosCivis))
			return false;
		AlunosCivis other = (AlunosCivis) obj;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getTipoEscolaridade() != other.getTipoEscolaridade())
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AlunosCivis [periodo=" + periodo + ", organizacaoMilitar=" + organizacaoMilitar + ", tipoEscolaridade="
				+ tipoEscolaridade + ", masculino=" + masculino + ", feminino=" + feminino + "]";
	}
	
}

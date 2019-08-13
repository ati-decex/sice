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

import br.mil.eb.decex.ati.enumerado.Ano;
import br.mil.eb.decex.ati.enumerado.TipoAlunosMilitaresOMDS;

/**
 *Classe para manipulação de dados de alunos militares OMDS
 * *
 * @author Luiz Augusto Lourenço do <b>Amparo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "alunosMilitaresOMDS.VisualizacaoAnual", query = "SELECT o FROM AlunosMilitaresOMDS o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
		@NamedQuery(name = "alunosMilitaresOMDS.listarPorOMEPeriodo", 
				query = "SELECT o FROM AlunosMilitaresOMDS o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND ano = :ano"),
		@NamedQuery(name = "alunosMilitaresOMDS.listarPorPeriodo", 
				query = "SELECT o FROM AlunosMilitaresOMDS o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao") 
})
@Table(name = "militaresOmds", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "ano","tipoalunosmilitaresomds", "organizacao_id"})})
public class AlunosMilitaresOMDS extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MILITARSOMDS_ID_GENERATOR", sequenceName = "MILITARSOMDS_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MILITARSOMDS_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "periodo_id")
	private Periodo periodo;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Enumerated(EnumType.STRING)
	private TipoAlunosMilitaresOMDS tipoAlunosMilitaresOMDS;

	@Column
	private Integer masculino;

	@Column
	private Integer feminino;
	
	@Enumerated(EnumType.STRING)
	private Ano ano;

	public AlunosMilitaresOMDS() {
	}

	/**
	 * Identificador de tabela. Código sequencial
	 * 
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
	 * Carrega a forca com valores do Enum tipoAlunosMilitaresOMDS
	 * @see br.mil.eb.decex.ati.enumerado.TipoAlunosMilitaresOMDS
	 * @returnf TipoAlunosMilitaresOMDS
	 */
	public TipoAlunosMilitaresOMDS getTipoAlunosMilitaresOMDS() {
		return tipoAlunosMilitaresOMDS;
	}
	public void setTipoAlunosMilitaresOMDS(TipoAlunosMilitaresOMDS tipoAlunosMilitaresOMDS) {
		this.tipoAlunosMilitaresOMDS = tipoAlunosMilitaresOMDS;
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
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	/**
	 * Organização militar que possui o efetivo informado
	 * 
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
	 * 
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
	 * 
	 * @return feminino
	 */
	public Integer getFeminino() {
		return feminino;
	}
	public void setFeminino(Integer feminino) {
		this.feminino = feminino;
	}

	/**
	 * Carrega a Ano com valores do Enum Ano
	 * @see br.mil.eb.decex.ati.enumerado.Ano
	 * @returnf Forca
	 */
	public Ano getAno() {
		return ano;
	}
	public void setAno(Ano ano) {
		this.ano = ano;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((tipoAlunosMilitaresOMDS == null) ? 0 : tipoAlunosMilitaresOMDS.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof AlunosMilitaresOMDS))
			return false;
		AlunosMilitaresOMDS other = (AlunosMilitaresOMDS) obj;
		if (getAno() != other.getAno())
			return false;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getTipoAlunosMilitaresOMDS() != other.getTipoAlunosMilitaresOMDS())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlunosMilitaresOMDS [id=" + id + ", periodo=" + periodo + ", organizacaoMilitar=" + organizacaoMilitar
				+ ", tipoAlunosMilitaresOMDS=" + tipoAlunosMilitaresOMDS + ", masculino=" + masculino + ", feminino="
				+ feminino + ", ano=" + ano + "]";
	}
	
}
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

import br.mil.eb.decex.ati.enumerado.SerieFundacaoOsorio;

/**
 * Base de lançamento para cadastro dos alunos da fundacaoOsosrio</p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "fundacaoOsorio.VisualizacaoAnual", query = "SELECT o FROM FundacaoOsorio o ORDER by periodo.ano , periodo.mes "),
    @NamedQuery(name="fundacaoOsorio.listarPorPeriodo", query="SELECT o FROM FundacaoOsorio o WHERE periodo = :periodo order by serieFundacao"),
    @NamedQuery(name="fundacaoOsorio.listarPorSerie", query="SELECT o FROM FundacaoOsorio o WHERE periodo = :periodo AND serieFundacao = :serie")
 })
@Table(name="fundacaoOsorio", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "seriefundacao"})})
public class FundacaoOsorio extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FUDACAOOSORIO_ID_GENERATOR", sequenceName="EFETIVOFUDACAOOSORIO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FUDACAOOSORIO_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;

	@Enumerated(EnumType.STRING)
	private SerieFundacaoOsorio serieFundacao;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	public  FundacaoOsorio() {

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
	 * armazena a quantidade de alunos masculinos da fundacao
	 * @return
	 */
	public Integer getMasculino() {
		return masculino;
	}
	public void setMasculino(Integer masculino) {
		this.masculino = masculino;
	}

	/**
	 * armazena a quantidade de alunos femininos da fundacao
	 * @return
	 */
	public Integer getFeminino() {
		return feminino;
	}
	public void setFeminino(Integer feminino) {
		this.feminino = feminino;
	}

	public SerieFundacaoOsorio getSerieFundacao() {
		return serieFundacao;
	}

	public void setSerieFundacao(SerieFundacaoOsorio serieFundacao) {
		this.serieFundacao = serieFundacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((serieFundacao == null) ? 0 : serieFundacao.hashCode());
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
		FundacaoOsorio other = (FundacaoOsorio) obj;
		if (feminino == null) {
			if (other.feminino != null)
				return false;
		} else if (!feminino.equals(other.feminino))
			return false;
		if (masculino == null) {
			if (other.masculino != null)
				return false;
		} else if (!masculino.equals(other.masculino))
			return false;
		if (serieFundacao == null) {
			if (other.serieFundacao != null)
				return false;
		} else if (!serieFundacao.equals(other.serieFundacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EfetivoFundacaoOsorio [serieFundacao=" + serieFundacao + ", masculino=" + masculino + ", feminino=" + feminino + "]";
	}
	
}

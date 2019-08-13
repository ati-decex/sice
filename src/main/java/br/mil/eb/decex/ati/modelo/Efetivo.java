package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/**
 * Base para lançamento do efetivo para cada OMDS.
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@MappedSuperclass
public abstract class Efetivo extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EFETIVO_ID_GENERATOR", sequenceName="EFETIVO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EFETIVO_ID_GENERATOR")		
	private Long id;		

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;	
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="linhaconfiguracao_id")	
	private LinhaConfiguracao linhaConfiguracao;	
	
	@ManyToOne(optional=true,  fetch=FetchType.EAGER)
	@JoinColumn(name="especialidade_id")	
	private Especialidade especialidade;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Column	
	private Integer quantidade;
	
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
	 * Linha de configuração para cada posto/graduação
	 * @see br.mil.eb.decex.ati.modelo.LinhaConfiguracao
	 * @return linha de configuração do posto/graduação
	 */
	public LinhaConfiguracao getLinhaConfiguracao() {
		return linhaConfiguracao;
	}
	public void setLinhaConfiguracao(LinhaConfiguracao linhaConfiguracao) {
		this.linhaConfiguracao = linhaConfiguracao;
	}

	/**
	 * Organização militar que possui o efetivo informado
	 * @return Ooganização militar que possui o efetivo informado
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	public Especialidade getEspecialidade() {
		if (especialidade==null){
			especialidade = new Especialidade();
		}
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	/**
	 * Quantidade do efetivo
	 * @return quantidade do efetivo
	 */
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}		
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((especialidade == null) ? 0 : especialidade.hashCode());
		result = prime * result + ((linhaConfiguracao == null) ? 0 : linhaConfiguracao.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
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
		Efetivo other = (Efetivo) obj;
		if (especialidade == null) {
			if (other.especialidade != null)
				return false;
		} else if (!especialidade.equals(other.especialidade))
			return false;
		if (linhaConfiguracao == null) {
			if (other.linhaConfiguracao != null)
				return false;
		} else if (!linhaConfiguracao.equals(other.linhaConfiguracao))
			return false;
		if (organizacaoMilitar == null) {
			if (other.organizacaoMilitar != null)
				return false;
		} else if (!organizacaoMilitar.equals(other.organizacaoMilitar))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Efetivo [periodo=" + periodo + ", linhaConfiguracao=" + linhaConfiguracao + "especialidade="+especialidade+", organizacaoMilitar=" 
				+ organizacaoMilitar +", quantidade=" + quantidade + "]";
	}	
}
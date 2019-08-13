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

import br.mil.eb.decex.ati.enumerado.TipoMilitaresNacoesAmigas;

/**
 * Base para lançamento dos Militares de Nações Amigas.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@NamedQueries({
    @NamedQuery(name="militaresNacoesAmigas.listarPorOMEPeriodo",
                query="SELECT o FROM MilitaresNacoesAmigas o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND tipoMilitarNacoesAmigas = :tipoMilitarNacoesAmigas"),
    @NamedQuery(name="militaresNacoesAmigas.listarPorOM",
    	query="SELECT o FROM MilitaresNacoesAmigas o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao"),
})
@Table(name="militaresNacoesAmigas", uniqueConstraints={@UniqueConstraint(columnNames={"periodo_id", "organizacao_id","tipomilitarnacoesamigas"})})
public class MilitaresNacoesAmigas extends BaseModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MILITARNACOESAMIGAS_ID_GENERATOR", sequenceName="MILITARNACOESAMIGAS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MILITARNACOESAMIGAS_ID_GENERATOR")	
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@Column
	private Integer total;
	
	@Column
	private Integer masculino;
	
	@Column
	private Integer feminino;
	
	@Enumerated(EnumType.STRING)
	private TipoMilitaresNacoesAmigas tipoMilitarNacoesAmigas;

	public MilitaresNacoesAmigas() {
		
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
	 * Valor do Efetivo total
	 * @return total
	 */
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * Tipo do Militar/Aluno de Nações amigas
	 * @see br.mil.eb.decex.ati.enumerado.TipoMilitaresNacoesAmigas
	 * @return
	 */
	public TipoMilitaresNacoesAmigas getTipoMilitarNacoesAmigas() {
		return tipoMilitarNacoesAmigas;
	}
	public void setTipoMilitarNacoesAmigas(TipoMilitaresNacoesAmigas tipoMilitarNacoesAmigas) {
		this.tipoMilitarNacoesAmigas = tipoMilitarNacoesAmigas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((tipoMilitarNacoesAmigas == null) ? 0 : tipoMilitarNacoesAmigas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof MilitaresNacoesAmigas))
			return false;
		MilitaresNacoesAmigas other = (MilitaresNacoesAmigas) obj;
		if (getTotal() != other.getTotal())
			return false;
		if (getFeminino() != other.getFeminino())
			return false;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getOrganizacaoMilitar() != other.getOrganizacaoMilitar())
			return false;
		if (getTipoMilitarNacoesAmigas() != other.getTipoMilitarNacoesAmigas())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MilitaresNacoesAmigas [organizacaoMilitar=" + organizacaoMilitar + ", total=" + total + ", masculino="
				+ masculino + ", feminino=" + feminino + ", tipoMilitarNacoesAmigas=" + tipoMilitarNacoesAmigas + "]";
	}

}

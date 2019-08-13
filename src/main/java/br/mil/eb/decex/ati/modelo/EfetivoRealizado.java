package br.mil.eb.decex.ati.modelo;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Lançamento do efetivo existente em cada OMDS.
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @modified Alteração de NamedQuery
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "EfetivoRealizado.VisualizacaoAnual", query = "SELECT o FROM EfetivoRealizado o WHERE organizacaoMilitar = :om ORDER by periodo.ano , periodo.mes "),
		@NamedQuery(name = "EfetivoRealizado.linhaConfiguracaoPorPeriodo", query = "SELECT e FROM EfetivoRealizado e JOIN FETCH e.linhaConfiguracao WHERE periodo_id = :periodo AND organizacao_id = :om"),
		@NamedQuery(name = "EfetivoRealizado.linhaConfiguracaoPorLinhaConfiguracao", query = "SELECT e FROM EfetivoRealizado e JOIN FETCH e.linhaConfiguracao WHERE periodo_id = :periodo AND organizacao_id = :om AND e.linhaConfiguracao = :lc ORDER BY e.linhaConfiguracao.ordem"),
		@NamedQuery(name = "EfetivoRealizado.listarPeriodo",	      query = "SELECT e FROM EfetivoRealizado e WHERE e.periodo = :periodo"),
		@NamedQuery(name = "EfetivoRealizado.listarPorEspecialidade", query = "SELECT e FROM EfetivoRealizado e LEFT JOIN e.especialidade JOIN FETCH e.periodo p JOIN FETCH e.organizacaoMilitar o WHERE e.linhaConfiguracao = :linhaConfiguracao AND ((e.especialidade = :especialidade) OR (:especialidade=0)) AND e.periodo = :periodo AND e.organizacaoMilitar = :om") })

@Table(name = "efetivorealizado", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "periodo_id", "linhaconfiguracao_id", "organizacao_id","especialidade_id" }) })
public class EfetivoRealizado extends Efetivo  implements Comparable<EfetivoRealizado> {

	private static final long serialVersionUID = 1L;

	private Integer masculino;

	private Integer feminino;

	public EfetivoRealizado() {

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

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((feminino == null) ? 0 : feminino.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EfetivoRealizado))
			return false;
		EfetivoRealizado other = (EfetivoRealizado) obj;
		if (getMasculino() != other.getMasculino())
			return false;
		if (getFeminino() != other.getFeminino())
			return false;
		return true;
	}
	
	@Override
	public int compareTo(EfetivoRealizado o) {
		if (this.getLinhaConfiguracao().getPostoGraduacao().getKey() == o.getLinhaConfiguracao().getPostoGraduacao().getKey()) {
			if (this.getLinhaConfiguracao().getLinha().getKey()      == o.getLinhaConfiguracao().getLinha().getKey()) {
				if (this.getLinhaConfiguracao().getTipo().getKey()   == o.getLinhaConfiguracao().getTipo().getKey()) {
					return this.getEspecialidade().getNomeEspecialidade().compareToIgnoreCase(o.getEspecialidade().getNomeEspecialidade());
				}
				else{
					return this.getLinhaConfiguracao().getTipo().getKey()	- o.getLinhaConfiguracao().getTipo().getKey();
				}
			}
			else{
				return this.getLinhaConfiguracao().getLinha().getKey()	- o.getLinhaConfiguracao().getLinha().getKey();
			}
		}
		else{
			return this.getLinhaConfiguracao().getPostoGraduacao().getKey()	- o.getLinhaConfiguracao().getPostoGraduacao().getKey(); 
		}
	}
	
}
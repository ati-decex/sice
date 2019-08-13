package br.mil.eb.decex.ati.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Lançamento do efetivo previsto em cada OMDS.
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */

@Entity
@NamedQueries({
		
		@NamedQuery(name = "EfetivoPrevisto.listarComLinhaConfiguracaoPorPeriodo", query = "SELECT e FROM EfetivoPrevisto e JOIN FETCH e.linhaConfiguracao JOIN FETCH e.organizacaoMilitar WHERE e.periodo = :periodo"),

		@NamedQuery(name = "EfetivoPrevisto.listarComLinhaConfiguracaoPorPeriodoELinhaConfiguracao", query = "SELECT e FROM EfetivoPrevisto e JOIN FETCH e.linhaConfiguracao JOIN FETCH e.qcp q WHERE e.linhaConfiguracao = :linhaConfiguracao AND q.organizacaoMilitar = :om AND e.qcp.ativo = true"),

		@NamedQuery(name = "EfetivoPrevisto.listarporQCPAtivo", query = "SELECT e FROM EfetivoPrevisto e JOIN FETCH e.qcp q WHERE q.organizacaoMilitar = :om AND e.qcp.ativo = true"),

		@NamedQuery(name = "EfetivoPrevisto.listarPorEspepecialidade", query = "SELECT e FROM EfetivoPrevisto e JOIN FETCH e.linhaConfiguracao LEFT JOIN e.especialidade JOIN FETCH e.qcp q WHERE e.linhaConfiguracao = :linhaConfiguracao AND ((e.especialidade = :especialidade) OR (:especialidade=0)) AND q.organizacaoMilitar = :om AND e.qcp.ativo = true" ) })

@Table(name = "efetivoprevisto", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "linhaconfiguracao_id", "organizacao_id", "qcp_id", "especialidade_id" }) })
public class EfetivoPrevisto extends Efetivo implements Comparable<EfetivoPrevisto> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qcp_id")
	private QCP qcp;

	public EfetivoPrevisto() {
	}

	/**
	 * QCP reservado para a Organização militar
	 * 
	 * @returnQCP
	 */
	public QCP getQcp() {
		return qcp;
	}

	public void setQcp(QCP qcp) {
		this.qcp = qcp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((qcp == null) ? 0 : qcp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EfetivoPrevisto other = (EfetivoPrevisto) obj;
		if (qcp == null) {
			if (other.qcp != null)
				return false;
		} else if (!qcp.equals(other.qcp))
			return false;
		return true;
	}

	@Override
	public int compareTo(EfetivoPrevisto o) {
		if (this.getLinhaConfiguracao().getPostoGraduacao().getKey() == o.getLinhaConfiguracao().getPostoGraduacao()
				.getKey()) {
			if (this.getLinhaConfiguracao().getLinha().getKey() == o.getLinhaConfiguracao().getLinha().getKey()) {
				if (this.getLinhaConfiguracao().getTipo().getKey() == o.getLinhaConfiguracao().getTipo().getKey()) {
					return this.getEspecialidade().getNomeEspecialidade()
							.compareToIgnoreCase(o.getEspecialidade().getNomeEspecialidade());
				} else {
					return this.getLinhaConfiguracao().getTipo().getKey() - o.getLinhaConfiguracao().getTipo().getKey();
				}
			} else {
				return this.getLinhaConfiguracao().getLinha().getKey() - o.getLinhaConfiguracao().getLinha().getKey();
			}
		} else {
			return this.getLinhaConfiguracao().getPostoGraduacao().getKey()
					- o.getLinhaConfiguracao().getPostoGraduacao().getKey();
		}
	}

	/*
	 * public int compareTo2(EfetivoPrevisto o) { if
	 * (this.getLinhaConfiguracao().getPostoGraduacao().getKey() !=
	 * o.getLinhaConfiguracao().getPostoGraduacao().getKey()){ return
	 * this.getLinhaConfiguracao().getPostoGraduacao().getKey() -
	 * o.getLinhaConfiguracao().getPostoGraduacao().getKey(); } else{ return
	 * this.getEspecialidade().getNomeEspecialidade().compareToIgnoreCase(o.
	 * getEspecialidade().getNomeEspecialidade()); } }
	 */
}
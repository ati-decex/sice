package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.mil.eb.decex.ati.enumerado.TipoOrganizacaoMilitar;

/**
 * Organizações Militares - Refere-se a pessoas, instituições, instalações,
 * equipamentos, veículos e qualquer outra coisa que fizer parte de uso
 * exclusivo direta e inseparável do militar
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @update Jonathan Philipe Amaral <b>Crespo</b>
 * @version 1.1
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "OrganizacaoMilitar.consultaOms", query = "SELECT o FROM OrganizacaoMilitar o WHERE o.tipo NOT IN ('DEPARTAMENTO', 'DIRETORIA') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.listarEstabelecimentosDeEnsino", query = "SELECT o FROM OrganizacaoMilitar o WHERE o.tipo IN ('ESTABELECIMENTO_ENSINO') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.listarEstabelecimentosDeEnsinoPorDiretoria", query = "SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.superiores s WHERE s = :diretoria and o.tipo IN ('ESTABELECIMENTO_ENSINO') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.comSuperior", query = "SELECT DISTINCT o FROM OrganizacaoMilitar o JOIN FETCH o.superiores WHERE o.sigla NOT LIKE ('% OM') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.porSubordinado", query = "SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.superiores s WHERE o = :sigla ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.porSuperior", query = "SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.superiores s WHERE s = :sigla ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.superiores", query = "SELECT o FROM OrganizacaoMilitar o WHERE tipo NOT IN ('ESTABELECIMENTO_ENSINO', 'VINCULADA') AND sigla NOT LIKE ('% OM') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.comEfetivoPrevisto", query = "SELECT DISTINCT o FROM OrganizacaoMilitar o INNER JOIN FETCH o.qcp q INNER JOIN q.efetivosPrevistos e ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.EstabelecimentosDeEnsinoSubordinados", query = "SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.superiores s WHERE s = :om AND o.tipo IN ('ESTABELECIMENTO_ENSINO') ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.porSigla", query = "SELECT o FROM OrganizacaoMilitar o WHERE tipo IN ('ESTABELECIMENTO_ENSINO') AND lower(sigla)  LIKE lower(:sigla) ORDER BY o.ordem"),
		@NamedQuery(name = "OrganizacaoMilitar.telas", query = "SELECT o FROM OrganizacaoMilitar o JOIN FETCH o.telas T WHERE o.id = :id ORDER BY T.ordem") })
// @NamedQuery(name = "OrganizacaoMilitar.registrosPorPeriodo", query = "SELECT
// om FROM OrganizacaoMilitar om RIGHT JOIN FETCH om.registros r WHERE"
// + "r.periodo = :periodo AND om.tipo = 'ESTABELECIMENTO_ENSINO' ORDER BY
// om.ordem")})

// select * from OrganizacaoMilitar om
// left join om.registros r on r.organizacao_id = om.id and r.periodo_id = 23
// where om.tipo = 'ESTABELECIMENTO_ENSINO'
// order by om.sigla,r.id

@Table(name = "organizacaomilitar", uniqueConstraints = { @UniqueConstraint(columnNames = { "sigla" }) })
public class OrganizacaoMilitar extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ORGANIZACAO_ID_GENERATOR", sequenceName = "ORGANIZACAO_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZACAO_ID_GENERATOR")
	private Long id;

	@Column
	private String sigla;

	@OneToMany(mappedBy = "organizacaoMilitar", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<QCP> qcp;

	@Column
	@Enumerated(EnumType.STRING)
	private TipoOrganizacaoMilitar tipo;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "SUPERIORES_SUBORDINADOS", joinColumns = @JoinColumn(name = "superior_subordinado_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "superior_id", referencedColumnName = "id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "superior_id", "superior_subordinado_id" }) })
	private List<OrganizacaoMilitar> superiores;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "CONFIGURACAO_TELA", joinColumns = @JoinColumn(name = "organizacao_id"), inverseJoinColumns = @JoinColumn(name = "tela_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "organizacao_id", "tela_id" }) })
	private List<Tela> telas;

	@Transient
	private List<RegistroAlteracaoEfetivoExistente> registros;

	@Transient
	private RegistroAlteracaoEfetivoExistente registroAlteracao;

	@Column
	private Integer ordem;

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
	 * 
	 * @return
	 */
	public List<RegistroAlteracaoEfetivoExistente> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroAlteracaoEfetivoExistente> registros) {
		this.registros = registros;
	}

	/**
	 * Sigla da organização militar
	 * 
	 * @return sigla da organização militar
	 */
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * Lista de boletins reservados anual que publicou o QCP para a Organização
	 * militar
	 * 
	 * @return Lista de QCP
	 */
	public List<QCP> getQcp() {
		if (qcp == null){
			qcp = new ArrayList<QCP>();
		}
		return qcp;
	}

	public void setQcp(List<QCP> qcp) {
		this.qcp = qcp;
	}

	/**
	 * Tipo da organização militar
	 * 
	 * @return tipo da organização militar
	 */
	public TipoOrganizacaoMilitar getTipo() {
		return tipo;
	}

	public void setTipo(TipoOrganizacaoMilitar tipo) {
		this.tipo = tipo;
	}

	/**
	 * Lista de organizações superiores a esta Organização militar
	 * 
	 * @return lista de organizações superiores a esta Organização militar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}

	public void setSuperiores(List<OrganizacaoMilitar> superiores) {
		this.superiores = superiores;
	}

	/**
	 * Lista de telas que esta organização militar deve cadastrar seu efetivo
	 * existente
	 * 
	 * @return lista de telas da organização militar
	 */
	public List<Tela> getTelas() {
		return telas;
	}

	public void setTelas(List<Tela> telas) {
		this.telas = telas;
	}

	/**
	 * Campo referênte a ordenção da Linha Configuração
	 * 
	 * @return Integer de ordenação
	 */
	public Integer getOrdem() {
		return ordem;
	}

	@Override
	public String toString() {
		return sigla;
	}

	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
	 * return result; }
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		OrganizacaoMilitar other = (OrganizacaoMilitar) obj;
		/*
		 * if (id == null) { if (other.id != null) return false; } else if
		 * (!id.equals(other.id)) return false;
		 */
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} else if (!sigla.equals(other.sigla)) {
			if (id == null) {
				if (other.id !=null){
					return false;
				}
			} else {
				if (!id.equals(other.id)){
					return false;
				}
			}
		}
		return true;
	}

	public RegistroAlteracaoEfetivoExistente getRegistroAlteracao() {
		return registroAlteracao;
	}

	public void setRegistroAlteracao(RegistroAlteracaoEfetivoExistente registroAlteracao) {
		this.registroAlteracao = registroAlteracao;
	}

}
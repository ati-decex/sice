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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.mil.eb.decex.ati.enumerado.LinhaQFE;
import br.mil.eb.decex.ati.enumerado.PostoGraduacaoQFE;

/**
 * Lançamento do efetivo existente em cada OMDS.
 * 
 * @version 1.0
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3º SGT
 */
@Entity
@NamedQueries({
@NamedQuery(name="efetivoQfe.listarPorPeriodo",
			query="SELECT o FROM EfetivoQFE o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao"),
@NamedQuery(name="efetivoQfe.listarPorOm",
			query="SELECT o FROM EfetivoQFE o WHERE periodo = :periodo AND organizacaoMilitarSuperior = :organizacao AND organizacaoMilitar =:organizacaoM"),
@NamedQuery(name="efetivoQfe.listarPorTodos",
			query="SELECT o FROM EfetivoQFE o WHERE periodo = :periodo AND organizacaoMilitar = :organizacao AND postoGraduacao = :postoGraduacao "
					+ "AND linha = :linha AND tipoQfe = :tipoQfe AND especialidadeQfe = :especialidadeQfe ")
})
@Table(name = "efetivoQFE", uniqueConstraints = {
	@UniqueConstraint(columnNames = { "periodo_id",   "organizacao_id"     ,   "especialidade_id"   , "postograduacao"  }) })
public class EfetivoQFE extends  BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "EFETIVOQFE_ID_GENERATOR", sequenceName = "EFETIVOQFE_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EFETIVOQFE_ID_GENERATOR")
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;	
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_superior_id")
	private OrganizacaoMilitar organizacaoMilitarSuperior;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_id")
	private TipoQFE tipoQfe;
	
	@Enumerated(EnumType.STRING)
	private PostoGraduacaoQFE postoGraduacao;
	
	@Enumerated(EnumType.STRING)
	private LinhaQFE linha;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="especialidade_id")	
	private EspecialidadeQFE especialidadeQfe;
	
	private Integer masculino;

//	private Integer feminino;

	public EfetivoQFE() {

	}
	
	
	
	public EspecialidadeQFE getEspecialidadeQfe() {
		return especialidadeQfe;
	}
	public void setEspecialidadeQfe(EspecialidadeQFE especialidadeQfe) {
		this.especialidadeQfe = especialidadeQfe;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public TipoQFE getTipoQfe() {
		return tipoQfe;
	}
	public void setTipoQfe(TipoQFE tipoQfe) {
		this.tipoQfe = tipoQfe;
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
//	public Integer getFeminino() {
//		return feminino;
//	}
//	public void setFeminino(Integer feminino) {
//		this.feminino = feminino;
//	}

	
	public PostoGraduacaoQFE getPostoGraduacao() {
		return postoGraduacao;
	}
	public void setPostoGraduacao(PostoGraduacaoQFE postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}

	public LinhaQFE getLinha() {
		return linha;
	}
	public void setLinha(LinhaQFE linha) {
		this.linha = linha;
	}



	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	public OrganizacaoMilitar getOrganizacaoMilitarSuperior() {
		return organizacaoMilitarSuperior;
	}
	public void setOrganizacaoMilitarSuperior(OrganizacaoMilitar organizacaoMilitarSuperior) {
		this.organizacaoMilitarSuperior = organizacaoMilitarSuperior;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((especialidadeQfe == null) ? 0 : especialidadeQfe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result + ((masculino == null) ? 0 : masculino.hashCode());
		result = prime * result + ((organizacaoMilitar == null) ? 0 : organizacaoMilitar.hashCode());
		result = prime * result + ((organizacaoMilitarSuperior == null) ? 0 : organizacaoMilitarSuperior.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((postoGraduacao == null) ? 0 : postoGraduacao.hashCode());
		result = prime * result + ((tipoQfe == null) ? 0 : tipoQfe.hashCode());
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
		EfetivoQFE other = (EfetivoQFE) obj;
		if (especialidadeQfe == null) {
			if (other.especialidadeQfe != null)
				return false;
		} else if (!especialidadeQfe.equals(other.especialidadeQfe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linha != other.linha)
			return false;
		if (masculino == null) {
			if (other.masculino != null)
				return false;
		} else if (!masculino.equals(other.masculino))
			return false;
		if (organizacaoMilitar == null) {
			if (other.organizacaoMilitar != null)
				return false;
		} else if (!organizacaoMilitar.equals(other.organizacaoMilitar))
			return false;
		if (organizacaoMilitarSuperior == null) {
			if (other.organizacaoMilitarSuperior != null)
				return false;
		} else if (!organizacaoMilitarSuperior.equals(other.organizacaoMilitarSuperior))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (postoGraduacao != other.postoGraduacao)
			return false;
		if (tipoQfe == null) {
			if (other.tipoQfe != null)
				return false;
		} else if (!tipoQfe.equals(other.tipoQfe))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "EfetivoQFE [id=" + id + ", periodo=" + periodo + ", organizacaoMilitar=" + organizacaoMilitar
				+ ", organizacaoMilitarSuperior=" + organizacaoMilitarSuperior + ", tipoQfe=" + tipoQfe
				+ ", postoGraduacao=" + postoGraduacao + ", linha=" + linha + ", especialidadeQfe=" + especialidadeQfe
				+ ", masculino=" + masculino + "]";
	}

	

	
}